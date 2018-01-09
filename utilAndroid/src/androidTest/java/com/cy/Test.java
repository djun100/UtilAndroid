package com.cy;

import android.test.InstrumentationTestCase;

import com.cy.security.UtilRSA;

/**
 * Created by cy on 2016/3/10.
 */
public class Test extends InstrumentationTestCase {

    public void test(){
        String crt="-----BEGIN CERTIFICATE-----\n" +
                "MIICcTCCAdoCCQC01YXqTV/YaTANBgkqhkiG9w0BAQsFADB9MQswCQYDVQQGEwJD\n" +
                "TjERMA8GA1UECAwITGlhb05pbmcxDzANBgNVBAcMBkRhTGlhbjENMAsGA1UECgwE\n" +
                "Sm95ZTEMMAoGA1UECwwDVEVDMQ0wCwYDVQQDDARUZXN0MR4wHAYJKoZIhvcNAQkB\n" +
                "Fg9iaW5nQGdvb29rdS5jb20wHhcNMTUwNjI1MDk0NjQwWhcNMTYwNjI0MDk0NjQw\n" +
                "WjB9MQswCQYDVQQGEwJDTjERMA8GA1UECAwITGlhb05pbmcxDzANBgNVBAcMBkRh\n" +
                "TGlhbjENMAsGA1UECgwESm95ZTEMMAoGA1UECwwDVEVDMQ0wCwYDVQQDDARUZXN0\n" +
                "MR4wHAYJKoZIhvcNAQkBFg9iaW5nQGdvb29rdS5jb20wgZ8wDQYJKoZIhvcNAQEB\n" +
                "BQADgY0AMIGJAoGBAKz8NwIn4l+MC/ShiPQz8Zxy2MnYOlJH/clqIVpeaTm5krBH\n" +
                "OnBSeTJStcRFCQj2DTWDzwVJPT6128RDSy/ZdSEoHJCeR76YbhQnU0gGQARPEjRY\n" +
                "2eASh6+CT/qkELIz/erz0/+disSamkSn5xZuyQ46ouTPvBjAh+ApFOPNBtiTAgMB\n" +
                "AAEwDQYJKoZIhvcNAQELBQADgYEAJUJunR4qjgbVbYUEWEMpqrVsB+RenZzPYNvE\n" +
                "g9D+BHk5GW2jhLn21CdPqwpDY6x8SRTLroN8SuRHT8z8KEpAqzjtx1stRRUY/q7t\n" +
                "h7OOTKON4ok4qbC2k8xvRECVO3VkiJ0aIceg2DfPaWSVFbtxMTaNvPeaVF5jC7QR\n" +
                "GMDu2WQ=\n" +
                "-----END CERTIFICATE-----";
        try {
            byte[] bytes= UtilRSA.encryptByPublicKey(crt.getBytes(),"sadfadf");
            System.err.println("bytes lenth:"+bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
