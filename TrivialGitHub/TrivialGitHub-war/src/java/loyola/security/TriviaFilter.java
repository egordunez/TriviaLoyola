/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loyola.security;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author hipnosapo
 */
public class TriviaFilter implements Filter {
    
    public static final String IS_LOGED_USER_KEY = "IS_LOGED_USER_KEY";
    public static final String USER_ID_KEY = "USER_ID_KEY";
    public static final String IS_LOGED_ADMIN_KEY = "IS_LOGED_ADMIN_KEY";
    public static final String SETTINGS_PATH = "/faces/settings.xhtml";
    public static final String PLAY_PATH = "/faces/play.xhtml";
    public static final String PLAY_CHAMP_PATH = "/faces/playchampionship.xhtml";
    public static final String PLAY_QUESTION_PATH = "/faces/play_question.xhtml";
    public static final String PLAY_CATEGORY_PATH = "/faces/questions_by_category.xhtml";
    public static final String ADMIN_USERS_PATH = "/faces/users_admin.xhtml";
    public static final String BANNED_USERS_PATH = "/faces/baned_users.xhtml";
    public static final String ADMIN_QUESTIONS_PATH = "/faces/questions.xhtml";
    public static final String CHAMP_ESPECT_PATH = "/faces/champespectator.xhtml";
    public static final String CHAMPIONSHIP_PATH = "/faces/championship.xhtml";
    public static final String PLAY_QUESTION_CHAMP_PATH = "/faces/play_question_champ.xhtml";
    public static final String LOAD_FROM_XLS_PATH = "/faces/loadfromxls.xhtml";
    public static final String LOAD_USER_FROM_XLS_PATH = "/faces/loaduserfromxls.xhtml";
    public static final String RANKING_PATH = "/faces/ranking.xhtml";
    public static final String PLAYING_CHAMP_PATH = "/faces/playingchamp.xhtml";
    public static final String LOGIN_PATH = "/faces/login.xhtml";
    public static final String LOGIN_URL = "login.xhtml";
    public static final String INDEX_PATH = "/faces/index.xhtml";
    public static final String INDEX_URL = "index.xhtml";
    public static final String UPDATE_ENTERPRISE_PATH = "/faces/update_enterprise.xhtml";
    public static final String STYLES_URL = "css";
    public static final String IMAGES_URL = "img";
    public static final String JS_URL = "js";
 
    
    private static final boolean debug = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public TriviaFilter() {
    }    
    
    private void doBeforeProcessing(RequestWrapper request, ResponseWrapper response)
            throws IOException, ServletException {
        if (debug) {
            log("TriviaFilter:DoBeforeProcessing");
        }

	// Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
	// For example, a filter that implements setParameter() on a request
        // wrapper could set parameters on the request before passing it on
        // to the filter chain.
	/*
         String [] valsOne = {"val1a", "val1b"};
         String [] valsTwo = {"val2a", "val2b", "val2c"};
         request.setParameter("name1", valsOne);
         request.setParameter("nameTwo", valsTwo);
         */
	// For example, a logging filter might log items on the request object,
        // such as the parameters.
	/*
         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         String values[] = request.getParameterValues(name);
         int n = values.length;
         StringBuffer buf = new StringBuffer();
         buf.append(name);
         buf.append("=");
         for(int i=0; i < n; i++) {
         buf.append(values[i]);
         if (i < n-1)
         buf.append(",");
         }
         log(buf.toString());
         }
         */
    }    
    
    private void doAfterProcessing(RequestWrapper request, ResponseWrapper response)
            throws IOException, ServletException {
        if (debug) {
            log("TriviaFilter:DoAfterProcessing");
        }

	// Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
	// For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
	/*
         for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         Object value = request.getAttribute(name);
         log("attribute: " + name + "=" + value.toString());

         }
         */
	// For example, a filter might append something to the response.
	/*
         PrintWriter respOut = new PrintWriter(response.getWriter());
         respOut.println("<p><strong>This has been appended by an intrusive filter.</strong></p>");
	
         respOut.println("<p>Params (after the filter chain):<br>");
         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         String values[] = request.getParameterValues(name);
         int n = values.length;
         StringBuffer buf = new StringBuffer();
         buf.append(name);
         buf.append("=");
         for(int i=0; i < n; i++) {login
         buf.append(values[i]);
         if (i < n-1)
         buf.append(",");
         }
         log(buf.toString());login
         respOut.println(buf.toString() + "<br>");
         }
         respOut.println("</p>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurslogin
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        trivialLoginRedirector(chain, (HttpServletRequest) request, (HttpServletResponse) response);
        
    }
    
    private boolean isLoged(HttpServletRequest httpRequest){
        if(httpRequest.getSession().getAttribute(IS_LOGED_USER_KEY)!= null){
            return (boolean) httpRequest.getSession().getAttribute(IS_LOGED_USER_KEY);
        }
        else return false;    
    }
    private boolean haveAccessAdminPage(HttpServletRequest httpRequest,String url){
        if( httpRequest.getSession().getAttribute(TriviaFilter.IS_LOGED_ADMIN_KEY) != null &&
                (boolean)httpRequest.getSession().getAttribute(TriviaFilter.IS_LOGED_ADMIN_KEY)){
            return true;
        }
        else{
            return !(
                    url.contains(LOAD_FROM_XLS_PATH)||
                    url.contains(LOAD_USER_FROM_XLS_PATH)||
                    url.contains(LOAD_FROM_XLS_PATH)||
                    url.contains(ADMIN_USERS_PATH)||
                    url.contains(ADMIN_QUESTIONS_PATH)||
                    url.contains(SETTINGS_PATH));
        }
    }
    private void trivialLoginRedirector(
            FilterChain chain,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse)throws IOException, ServletException{
        
        String reqURL = httpRequest.getRequestURL().toString();
        if( 
                reqURL.contains(STYLES_URL)||
                reqURL.contains(IMAGES_URL)||
                reqURL.contains(JS_URL)){
            chain.doFilter(httpRequest, httpResponse);
        }
        else if(!reqURL.contains(LOGIN_URL)){
            if(!isLoged(httpRequest)||!haveAccessAdminPage(httpRequest,reqURL)){
                httpRequest.getSession().invalidate();
                httpResponse.sendRedirect(httpRequest.getContextPath() + LOGIN_PATH);
            }
            else{
                chain.doFilter(httpRequest, httpResponse);
            }
        }
        else if(isLoged(httpRequest)){
            httpResponse.sendRedirect(httpRequest.getContextPath() + INDEX_PATH);
        }
        else{
            try{
                chain.doFilter(httpRequest, httpResponse);
            }catch(IOException | ServletException io){
                httpResponse.sendRedirect(httpRequest.getContextPath() + LOGIN_PATH);
                System.err.println("IOException or ServletException:"+io.getMessage());
            }            
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("TriviaFilter: Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("TriviaFilter()");
        }
        StringBuffer sb = new StringBuffer("TriviaFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
        
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }

    /**
     * This request wrapper class extends the support class
     * HttpServletRequestWrapper, which implements all the methods in the
     * HttpServletRequest interface, as delegations to the wrapped request. You
     * only need to override the methods that you need to change. You can get
     * access to the wrapped request using the method getRequest()
     */
    class RequestWrapper extends HttpServletRequestWrapper {
        
        public RequestWrapper(HttpServletRequest request) {
            super(request);
        }

	// You might, for example, wish to add a setParameter() method. To do this
        // you must also override the getParameter, getParameterValues, getParameterMap,
        // and getParameterNames methods.
        protected Hashtable localParams = null;
        
        public void setParameter(String name, String[] values) {
            if (debug) {
                System.out.println("TriviaFilter::setParameter(" + name + "=" + values + ")" + " localParams = " + localParams);
            }
            
            if (localParams == null) {
                localParams = new Hashtable();
                // Copy the parameters from the underlying request.
                Map wrappedParams = getRequest().getParameterMap();
                Set keySet = wrappedParams.keySet();
                for (Iterator it = keySet.iterator(); it.hasNext();) {
                    Object key = it.next();
                    Object value = wrappedParams.get(key);
                    localParams.put(key, value);
                }
            }
            localParams.put(name, values);
        }
        
        @Override
        public String getParameter(String name) {
            if (debug) {
                System.out.println("TriviaFilter::getParameter(" + name + ") localParams = " + localParams);
            }
            if (localParams == null) {
                return getRequest().getParameter(name);
            }
            Object val = localParams.get(name);
            if (val instanceof String) {
                return (String) val;
            }
            if (val instanceof String[]) {
                String[] values = (String[]) val;
                return values[0];
            }
            return (val == null ? null : val.toString());
        }
        
        @Override
        public String[] getParameterValues(String name) {
            if (debug) {
                System.out.println("TriviaFilter::getParameterValues(" + name + ") localParams = " + localParams);
            }
            if (localParams == null) {
                return getRequest().getParameterValues(name);
            }
            return (String[]) localParams.get(name);
        }
        
        @Override
        public Enumeration getParameterNames() {
            if (debug) {
                System.out.println("TriviaFilter::getParameterNames() localParams = " + localParams);
            }
            if (localParams == null) {
                return getRequest().getParameterNames();
            }
            return localParams.keys();
        }        
        
        @Override
        public Map getParameterMap() {
            if (debug) {
                System.out.println("TriviaFilter::getParameterMap() localParams = " + localParams);
            }
            if (localParams == null) {
                return getRequest().getParameterMap();
            }
            return localParams;
        }
    }

    /**
     * This response wrapper class extends the support class
     * HttpServletResponseWrapper, which implements all the methods in the
     * HttpServletResponse interface, as delegations to the wrapped response.
     * You only need to override the methods that you need to change. You can
     * get access to the wrapped response using the method getResponse()
     */
    class ResponseWrapper extends HttpServletResponseWrapper {
        
        public ResponseWrapper(HttpServletResponse response) {
            super(response);            
        }

	// You might, for example, wish to know what cookies were set on the response
        // as it went throught the filter chain. Since HttpServletRequest doesn't
        // have a get cookies method, we will need to store them locally as they
        // are being set.
	/*
         protected Vector cookies = null;
	
         // Create a new method that doesn't exist in HttpServletResponse
         public Enumeration getCookies() {
         if (cookies == null)
         cookies = new Vector();
         return cookies.elements();
         }
	
         // Override this method from HttpServletResponse to keep track
         // of cookies locally as well as in the wrapped response.
         public void addCookie (Cookie cookie) {
         if (cookies == null)
         cookies = new Vector();
         cookies.add(cookie);
         ((HttpServletResponse)getResponse()).addCookie(cookie);
         }
         */
    }
    
}
