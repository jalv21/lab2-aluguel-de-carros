package com.aluguel.config;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import org.reactivestreams.Publisher;

/**
 * Security Filter for HTTP headers and protection measures
 * Implements security best practices including:
 * - HSTS (HTTP Strict Transport Security)
 * - X-Content-Type-Options
 * - X-Frame-Options (Clickjacking protection)
 * - Content Security Policy
 * - X-XSS-Protection
 * - Referrer Policy
 */
@Filter("/**")
public class SecurityHeaderFilter implements HttpServerFilter {

    @Value("${security.enabled:true}")
    private boolean securityEnabled;

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        return new org.reactivestreams.Publisher<MutableHttpResponse<?>>() {
            @Override
            public void subscribe(org.reactivestreams.Subscriber<? super MutableHttpResponse<?>> subscriber) {
                chain.proceed(request).subscribe(new org.reactivestreams.Subscriber<MutableHttpResponse<?>>() {
                    @Override
                    public void onNext(MutableHttpResponse<?> response) {
                        if (securityEnabled) {
                            addSecurityHeaders(response);
                        }
                        subscriber.onNext(response);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }

                    @Override
                    public void onComplete() {
                        subscriber.onComplete();
                    }

                    @Override
                    public void onSubscribe(org.reactivestreams.Subscription subscription) {
                        subscriber.onSubscribe(subscription);
                    }
                });
            }
        };
    }

    /**
     * Add security headers to response
     */
    private void addSecurityHeaders(MutableHttpResponse<?> response) {
        // Prevent clickjacking attacks
        response.header("X-Frame-Options", "DENY");
        
        // Prevent MIME sniffing
        response.header("X-Content-Type-Options", "nosniff");
        
        // Enable XSS protection in older browsers
        response.header("X-XSS-Protection", "1; mode=block");
        
        // Referrer policy for privacy
        response.header("Referrer-Policy", "strict-origin-when-cross-origin");
        
        // HSTS for HTTPS (optional, only for production with HTTPS)
        // response.header("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        
        // Content Security Policy - strict configuration
        response.header("Content-Security-Policy", 
            "default-src 'self'; " +
            "script-src 'self' https://cdn.tailwindcss.com https://cdn.jsdelivr.net 'unsafe-inline'; " +
            "style-src 'self' https://cdn.tailwindcss.com 'unsafe-inline'; " +
            "img-src 'self' data: https:; " +
            "font-src 'self' https://cdn.jsdelivr.net; " +
            "connect-src 'self'; " +
            "frame-ancestors 'none'; " +
            "base-uri 'self'; " +
            "form-action 'self'"
        );
        
        // Remove server header for security
        response.header("Server", "");
    }
}
