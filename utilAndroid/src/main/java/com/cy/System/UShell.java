package com.cy.System;

import android.text.TextUtils;

import com.cy.io.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * <ul>
 * <strong>Check root</strong>
 * <li>{@link com.cy.System.UShell#checkRootPermission()}</li>
 * </ul>
 * <ul>
 * <strong>Execte command</strong>
 * <li>{@link com.cy.System.UShell#execCommand(String, boolean)}</li>
 * <li>{@link com.cy.System.UShell#execCommand(String, boolean, boolean)}</li>
 * <li>{@link com.cy.System.UShell#execCommand(List, boolean)}</li>
 * <li>{@link com.cy.System.UShell#execCommand(List, boolean, boolean)}</li>
 * <li>{@link com.cy.System.UShell#execCommand(String[], boolean)}</li>
 * <li>{@link com.cy.System.UShell#execCommand(String[], boolean, boolean)}</li>
 * </ul>
 * @author cy
 */
public class UShell {

    public static final String COMMAND_SU       = "su";
    public static final String COMMAND_SH       = "sh";
    public static final String COMMAND_EXIT     = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    /** 判断手机是否root，不弹出root请求框
     * http://www.cnblogs.com/waylife/p/android_root_check.html
     * 权限说明
     * http://cn.linux.vbird.org/linux_basic/0210filepermission.php
     * */
    public static boolean isRooted() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        if (new File(binPath).exists() && isExecutable(binPath))
            return true;
        if (new File(xBinPath).exists() && isExecutable(xBinPath))
            return true;
        return false;
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = in.readLine();
            Log.i( str);
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                //当s权限在文件组 x 权限上时，例如：-rwx--s--x，此时称为Set GID，简称为SGID的特殊权限， 执行者在执行该文件时将具有该文件所属组的权限。
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(p!=null){
                p.destroy();
            }
        }
        return false;
    }
    /**
     * check whether has root permission
     *
     * @return
     */
    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    /**
     * execute shell command, default return result msg
     *
     * @param command command
     * @param isRoot whether need to run with root
     * @return
     * @see com.cy.System.UShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[] {command}, isRoot, true);
    }

    /**
     * execute shell commands, default return result msg
     *
     * @param commands command list
     * @param isRoot whether need to run with root
     * @return
     * @see com.cy.System.UShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands == null ? null : commands.toArray(new String[] {}), isRoot, true);
    }

    /**
     * execute shell commands, default return result msg
     *
     * @param commands command array
     * @param isRoot whether need to run with root
     * @return
     * @see com.cy.System.UShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    /**
     * execute shell command
     *
     * @param command command
     * @param isRoot whether need to run with root
     * @param isNeedResultMsg whether need result msg
     * @return
     * @see com.cy.System.UShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[] {command}, isRoot, isNeedResultMsg);
    }

    /**
     * execute shell commands
     *
     * @param commands command list
     * @param isRoot whether need to run with root
     * @param isNeedResultMsg whether need result msg
     * @return
     * @see com.cy.System.UShell#execCommand(String[], boolean, boolean)
     */
    public static CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(commands == null ? null : commands.toArray(new String[] {}), isRoot, isNeedResultMsg);
    }

    /**
     * execute shell commands
     *
     * @param commands command array
     * @param isRoot whether need to run with root
     * @param isNeedResultMsg whether need result msg
     * @return <ul>
     *         <li>if isNeedResultMsg is false, {@link CommandResult#successMsg} is null and
     *         {@link CommandResult#errorMsg} is null.</li>
     *         <li>if {@link CommandResult#result} is -1, there maybe some excepiton.</li>
     *         </ul>
     */
    public static CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        boolean hasSuccessMsg=false;
        StringBuilder errorMsg = null;
        boolean hasErrorMsg=false;

        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }

                // donnot use os.writeBytes(commmand), avoid chinese charset error
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();

            result = process.waitFor();
            // get command result
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;


                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s).append("\n");
                    hasSuccessMsg=true;
                }
                if (hasSuccessMsg){
                    successMsg.deleteCharAt(successMsg.length()-1);
                }


                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s).append("\n");
                    hasErrorMsg=true;
                }
                if (hasErrorMsg){
                    errorMsg.deleteCharAt(errorMsg.length()-1);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (process != null) {
                process.destroy();
            }
        }

        CommandResult commandResult= new CommandResult(result,
                successMsg == null ? null : successMsg.toString(),
                errorMsg == null ? null : errorMsg.toString());


        for (int i = 0; i < commands.length; i++) {
            Log.w((i == 0 ? "cmd:" : "") + commands[i]);
        }
        Log.w((!TextUtils.isEmpty(commandResult.errorMsg)?("errorMsg:"+commandResult.errorMsg):"")
                +(!TextUtils.isEmpty(commandResult.successMsg)?(" successMsg:"+commandResult.successMsg):"")
                +" result:"+commandResult.result);


        return commandResult;
    }

    /**
     * result of command
     * <ul>
     * <li>{@link CommandResult#result} means result of command, 0 means normal, else means error, same to excute in
     * linux shell</li>
     * <li>{@link CommandResult#successMsg} means success message of command result</li>
     * <li>{@link CommandResult#errorMsg} means error message of command result</li>
     * </ul>
     *
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
     */
    public static class CommandResult {

        /** result of command **/
        public int    result;
        /** success message of command result **/
        public String successMsg;
        /** error message of command result **/
        public String errorMsg;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }

        @Override
        public String toString() {
//            return (!TextUtils.isEmpty(errorMsg)?errorMsg:"")
//                    +(!TextUtils.isEmpty(successMsg)?successMsg:"");
            return errorMsg+successMsg;
        }
    }
}
