package com.sfeir.tutorials.server.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * This is the crawler filter. its role is to detect the Google Search Engine
 * requests and render the HTML code associated to the requests (return a
 * response that will not contain javascript code)
 * 
 * @author Oussama Zoghlami
 * @date 13/12/2011
 */

public class CrawlerFilter implements Filter {

	private static final String ESCAPED_FRAGMENT_PARAMETER = "_escaped_fragment_";
	private static final String HTML_CONTENT_TYPE = "text/html;charset=UTF-8";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String queryString = req.getQueryString();
		final String requestURI = req.getRequestURI();
		// if the request is associated to a search engine
		if ((queryString != null) && (queryString.contains(ESCAPED_FRAGMENT_PARAMETER))) {
			try {
				String requestUrl = constructRequestURL(req, queryString, requestURI);
				HtmlPage page = generateHtmlPage(requestUrl);
				res.setContentType(HTML_CONTENT_TYPE);
				PrintWriter out = res.getWriter();
				out.println(page.asXml());
			} catch (Throwable e) {
				System.err.println("Exception dans le filtre Crawler");
				e.printStackTrace(System.err);
			}
		}
		// Else pass the request
		else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * Method allowing to get the HTML Code associated to a given URL
	 * 
	 * @param requestUrl
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	private HtmlPage generateHtmlPage(String requestUrl) throws IOException, MalformedURLException {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setJavaScriptEnabled(true);
		HtmlPage page = webClient.getPage(requestUrl);
		webClient.setTimeout(10000);
		webClient.closeAllWindows();
		return page;
	}

	/**
	 * Method allowing to construct the URL associated to a search engine
	 * request
	 * 
	 * @param req
	 * @param queryString
	 * @param requestURI
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String constructRequestURL(HttpServletRequest req, String queryString, final String requestURI)
			throws UnsupportedEncodingException {
		StringBuilder pageNameSb = new StringBuilder("http://");
		pageNameSb.append(req.getServerName());
		if (req.getServerPort() != 0) {
			pageNameSb.append(":");
			pageNameSb.append(req.getServerPort());
		}
		pageNameSb.append(requestURI);
		queryString = rewriteQueryString(queryString);
		pageNameSb.append(queryString);
		return pageNameSb.toString();
	}

	/**
	 * Method allowing to reformat a query string associated to a search engine
	 * request
	 * 
	 * @param queryString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String rewriteQueryString(String queryString) throws UnsupportedEncodingException {
		int index = queryString.indexOf("&" + ESCAPED_FRAGMENT_PARAMETER + "=");
		int length = 20;
		if (index == -1) {
			index = queryString.indexOf(ESCAPED_FRAGMENT_PARAMETER + "=");
			length = 19;
		}
		if (index != -1) {
			StringBuilder queryStringSb = new StringBuilder();
			if (index > 0) {
				queryStringSb.append("?").append(queryString.substring(0, index));
			}
			queryStringSb.append("#!").append(
					URLDecoder.decode(queryString.substring(index + length, queryString.length()), "UTF-8"));
			return queryStringSb.toString();
		}
		return queryString;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
