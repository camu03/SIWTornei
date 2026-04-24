package it.uniroma3.siw.siwtornei.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // 1. Se l'utente era stato intercettato da Spring (es. ha provato ad andare su /admin)
        if (savedRequest != null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        // 2. Se l'utente ha cliccato "Login" volontariamente da una pagina pubblica (es. Match Center)
        String targetUrl = (String) request.getSession().getAttribute("url_prior_login");
        if (targetUrl != null) {
            request.getSession().removeAttribute("url_prior_login");
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } else {
            // 3. Fallback alla Home se non sappiamo da dove viene
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}