package com.test.cle.salesforce.rest;

public class RestClient {

    /**
     * public String httpURL = null; public String userName = null; public String password = null;
     *
     * <p>public String getHttpURL() { return httpURL; }
     *
     * <p>public void setHttpURL(String httpURL) { this.httpURL = httpURL; }
     *
     * <p>public String getUserName() { return userName; }
     *
     * <p>public void setUserName(String userName) { this.userName = userName; }
     *
     * <p>public String getPassword() { return password; }
     *
     * <p>public void setPassword(String password) { this.password = password; }
     *
     * <p>public void httpGetRequest(String url) throws IOException { CloseableHttpClient httpclient =
     * HttpClients.createDefault(); CloseableHttpResponse closeableHttpResponse =null;
     *
     * <p>try { HttpGet httpGet = new HttpGet(url); closeableHttpResponse =
     * httpclient.execute(httpGet);
     *
     * <p>System.out.println(closeableHttpResponse.getStatusLine()); HttpEntity entity1 =
     * closeableHttpResponse.getEntity();
     *
     * <p>EntityUtils.consume(entity1); } catch (Exception e) { e.printStackTrace(); } finally {
     * closeableHttpResponse.close(); }
     *
     * <p>}
     *
     * <p>public void httpPostRequest(String getUrl, String postUrl) throws IOException {
     * CloseableHttpClient httpclient = HttpClients.createDefault(); try { HttpGet httpGet = new
     * HttpGet(getUrl); CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpGet);
     *
     * <p>try { System.out.println(closeableHttpResponse.getStatusLine()); HttpEntity entity1 =
     * closeableHttpResponse.getEntity();
     *
     * <p>EntityUtils.consume(entity1); } finally { closeableHttpResponse.close(); }
     *
     * <p>HttpPost httpPost = new HttpPost(postUrl); List<NameValuePair> nvps = new
     * ArrayList<NameValuePair>(); nvps.add(new BasicNameValuePair("username", userName));
     * nvps.add(new BasicNameValuePair("password", password)); httpPost.setEntity(new
     * UrlEncodedFormEntity(nvps)); CloseableHttpResponse response2 = httpclient.execute(httpPost);
     *
     * <p>try { System.out.println(response2.getStatusLine()); HttpEntity httpEntity =
     * response2.getEntity();
     *
     * <p>EntityUtils.consume(httpEntity); } finally { response2.close(); } } finally {
     * httpclient.close(); } }
     */
}
