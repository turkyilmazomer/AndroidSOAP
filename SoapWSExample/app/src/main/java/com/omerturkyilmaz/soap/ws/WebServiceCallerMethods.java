package com.omerturkyilmaz.soap.ws;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.ksoap2.transport.KeepAliveHttpsTransportSE;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



public class WebServiceCallerMethods {


    SoapSerializationEnvelope envelope;
    HttpTransportSE androidHttpTransport;
    HttpsTransportSE androidHttpsTransport;


    public WebServiceCallerMethods() {
        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;

        if (Settings.SSL) {
            allowAllSSL();
            androidHttpsTransport = new KeepAliveHttpsTransportSE(Settings.serverURL, 443, Settings.servicePath, 30000);
            androidHttpsTransport.debug = true;
        } else {
            androidHttpTransport = new HttpTransportSE(Settings.serverURL + Settings.servicePath, 30000);
            envelope.encodingStyle = SoapEnvelope.ENC;
            envelope.setAddAdornments(false);
            envelope.implicitTypes = false;
        }

    }


    /*-----Below code is for trust connection with ssl-------*/
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TrustManager[] trustManagers;

    public static class _FakeX509TrustManager implements
            X509TrustManager {
        private static final X509Certificate[] _AcceptedIssuers =
                new X509Certificate[]{};

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public boolean isClientTrusted(X509Certificate[] chain) {
            return (true);
        }

        public boolean isServerTrusted(X509Certificate[] chain) {
            return (true);
        }

        public X509Certificate[] getAcceptedIssuers() {
            return (_AcceptedIssuers);
        }
    }

    public static void allowAllSSL() {

        HttpsURLConnection.setDefaultHostnameVerifier(new
                                                                            HostnameVerifier() {
                                                                                public boolean verify(String hostname, SSLSession
                                                                                        session) {
                                                                                    return true;
                                                                                }
                                                                            });

        SSLContext context = null;

        if (trustManagers == null) {
            trustManagers = new TrustManager[]{new
                    _FakeX509TrustManager()};
        }

        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {

        } catch (KeyManagementException e) {

        }

        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }

    public String GetEmployee() {

        String methodName = "GetEmployee";
        SoapObject request = new SoapObject(Settings.nameSpace, methodName);
        envelope.setOutputSoapObject(request);

        try {

            if (Settings.SSL) {
                androidHttpsTransport.call(Settings.nameSpace + methodName, envelope);
            } else {
                androidHttpTransport.call(Settings.nameSpace + methodName, envelope);
            }
            disconnect();
            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject soapObject = (SoapObject) envelope.bodyIn;
                return soapObject.getPropertyAsString(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return wsException(ex, methodName);
        }
        return "";
    }

    public String GetEmployeeById(int EmployeeId) {
        String methodName = "GetEmployeeById";
        SoapObject request = new SoapObject(Settings.nameSpace, methodName);
        request.addProperty("EmployeeId", EmployeeId);

        envelope.setOutputSoapObject(request);
        try {
            if (Settings.SSL) {
                androidHttpsTransport.call(Settings.nameSpace + methodName, envelope);
            } else {
                androidHttpTransport.call(Settings.nameSpace + methodName, envelope);
            }
            disconnect();
            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject soapObject = (SoapObject) envelope.bodyIn;
                return soapObject.getPropertyAsString(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return wsException(ex, methodName);
        }
        return "";
    }

    private String wsException(Exception ex, String methodNameStr) {
        String result = "";
        try {

            String fileName = ex.getStackTrace()[0].getFileName();
            String methodName = fileName + " - " + ex.getStackTrace()[0].getMethodName();
            String errStr = "LineNumber:" + ex.getStackTrace()[0].getLineNumber() + " Err: " + ex.toString();
            return "WebServiceException" + errStr;
        } catch (Exception ex1) {
        }
        return result;
    }

    public void disconnect() {
        try {
            if (Settings.SSL) {
                if (androidHttpsTransport != null) {
                    androidHttpsTransport.reset();
                    androidHttpsTransport.getConnection().disconnect();
                }

            } else {
                if (androidHttpTransport != null) {
                    androidHttpTransport.reset();
                    androidHttpTransport.getConnection().disconnect();
                }
            }

            /*
            https://stackoverflow.com/questions/12543903/close-http-connection-after-web-service-call-in-android
            if (httpTransport != null) {
                httpTransport.reset();
                try {
                    httpTransport.getConnection().disconnect();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }*/


        } catch (Exception ex) {

        }

    }
}
