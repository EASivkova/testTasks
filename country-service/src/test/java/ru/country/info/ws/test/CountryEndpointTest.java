package ru.country.info.ws.test;

import javax.xml.transform.Source;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.xml.transform.StringSource;

public class CountryEndpointTest {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "classpath:/country-service-test.xml");

    private MockWebServiceClient mockClient;

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() throws Exception {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @AfterClass
    public static void afterClass() {
    }

    @Test
    public void testNormalTransport() throws Exception {
        Source requestPayload = new StringSource(
                "<CountryRequest xmlns=\"urn:country-input:v0.1\">"
                + "<name>США</name>"
              + "</CountryRequest>");
        Source responcePayload = new StringSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        		+ "<CountryResponse xmlns=\"urn:country-input:v0.1\">"
        			+ "<body>"
        				+ "<countryInfo id=\"8\">"
        					+ "<courses>"
        						+ "<vname>Доллар США</vname><vnom>1</vnom><vcurs>62.7363</vcurs><vcode>840</vcode><vchcode>USD</vchcode>"
        					+ "</courses>"
        					+ "<weather>"
        						+ "<forecast><time>2015-01-13T07:00:00</time><tod>1</tod><t>-1.7763625383377075</t><p>1027.1060791015625</p><cl>36</cl><prc>0.0</prc><prct>0</prct><dd>359.3390197753906</dd><ff>4.146028995513916</ff><st>0</st><humidity>71</humidity></forecast><forecast><time>2015-01-13T13:00:00</time><tod>2</tod><t>0.18277916312217712</t><p>1028.9288330078125</p><cl>0</cl><prc>0.0</prc><prct>0</prct><dd>15.557238578796387</dd><ff>3.9528002738952637</ff><st>0</st><humidity>39</humidity></forecast><forecast><time>2015-01-13T19:00:00</time><tod>3</tod><t>-3.121979236602783</t><p>1030.1585693359375</p><cl>3</cl><prc>0.0</prc><prct>0</prct><dd>42.492584228515625</dd><ff>1.9886953830718994</ff><st>0</st><humidity>39</humidity></forecast><forecast><time>2015-01-14T01:00:00</time><tod>0</tod><t>-3.3553707599639893</t><p>1028.5137939453125</p><cl>45</cl><prc>5.833333125337958E-4</prc><prct>2</prct><dd>57.77251434326172</dd><ff>2.9087941646575928</ff><st>0</st><humidity>39</humidity></forecast><forecast><time>2015-01-14T07:00:00</time><tod>1</tod><t>-2.989112615585327</t><p>1025.6484375</p><cl>99</cl><prc>0.24108333885669708</prc><prct>2</prct><dd>31.19981575012207</dd><ff>2.017484188079834</ff><st>0</st><humidity>54</humidity></forecast><forecast><time>2015-01-14T13:00:00</time><tod>2</tod><t>-0.9695624709129333</t><p>1022.0579833984375</p><cl>96</cl><prc>0.23383332788944244</prc><prct>2</prct><dd>9.515069961547852</dd><ff>2.0129992961883545</ff><st>0</st><humidity>70</humidity></forecast><forecast><time>2015-01-14T19:00:00</time><tod>3</tod><t>-0.6002374887466431</t><p>1020.094970703125</p><cl>92</cl><prc>0.022166667506098747</prc><prct>2</prct><dd>350.9740295410156</dd><ff>1.070496678352356</ff><st>0</st><humidity>77</humidity></forecast><forecast><time>2015-01-15T01:00:00</time><tod>0</tod><t>-1.5590791702270508</t><p>1018.43115234375</p><cl>80</cl><prc>0.0</prc><prct>0</prct><dd>334.2157897949219</dd><ff>1.9475715160369873</ff><st>0</st><humidity>82</humidity></forecast><forecast><time>2015-01-15T07:00:00</time><tod>1</tod><t>-1.612429141998291</t><p>1018.0440673828125</p><cl>61</cl><prc>0.011083333753049374</prc><prct>2</prct><dd>324.6296081542969</dd><ff>1.7964088916778564</ff><st>0</st><humidity>79</humidity></forecast><forecast><time>2015-01-15T13:00:00</time><tod>2</tod><t>4.168204307556152</t><p>1015.5026245117188</p><cl>4</cl><prc>0.0</prc><prct>0</prct><dd>272.23052978515625</dd><ff>1.7188386917114258</ff><st>0</st><humidity>53</humidity></forecast><forecast><time>2015-01-16T01:00:00</time><tod>0</tod><t>-2.2743375301361084</t><p>1012.9384155273438</p><cl>0</cl><prc>0.0</prc><prct>0</prct><dd>292.5472106933594</dd><ff>1.8920304775238037</ff><st>0</st><humidity>68</humidity></forecast><forecast><time>2015-01-16T13:00:00</time><tod>2</tod><t>6.325662612915039</t><p>1011.2095947265625</p><cl>0</cl><prc>0.0</prc><prct>0</prct><dd>288.44671630859375</dd><ff>5.5491042137146</ff><st>0</st><humidity>46</humidity></forecast><forecast><time>2015-01-17T01:00:00</time><tod>0</tod><t>-2.385512590408325</t><p>1019.3545532226562</p><cl>0</cl><prc>0.0</prc><prct>0</prct><dd>12.361037254333496</dd><ff>2.0966758728027344</ff><st>0</st><humidity>79</humidity></forecast><forecast><time>2015-01-17T13:00:00</time><tod>2</tod><t>4.9280290603637695</t><p>1018.2583618164062</p><cl>0</cl><prc>0.0</prc><prct>0</prct><dd>172.79156494140625</dd><ff>4.040521144866943</ff><st>0</st><humidity>56</humidity></forecast><forecast><time>2015-01-18T01:00:00</time><tod>0</tod><t>3.1656625270843506</t><p>1010.7423095703125</p><cl>0</cl><prc>0.0</prc><prct>0</prct><dd>203.62045288085938</dd><ff>4.782652378082275</ff><st>0</st><humidity>84</humidity></forecast>"
        					+ "</weather>"
        				+ "</countryInfo>"
        			+ "</body>"
        		+ "</CountryResponse>");
        mockClient.sendRequest(RequestCreators.withPayload(requestPayload)).andExpect(
                ResponseMatchers.payload(responcePayload));

    }
}
