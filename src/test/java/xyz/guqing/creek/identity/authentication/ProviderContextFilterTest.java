package xyz.guqing.creek.identity.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Tests for {@link ProviderContextFilter}.
 *
 * @author guqing
 * @since 2.0.0
 */
public class ProviderContextFilterTest {

    @AfterEach
    public void cleanup() {
        ProviderContextHolder.resetProviderContext();
    }

    @Test
    public void constructorWhenProviderSettingsNullThenThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new ProviderContextFilter(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("providerSettings cannot be null");
    }

    @Test
    public void doFilterWhenIssuerConfiguredThenUsed() throws Exception {
        String issuer = "https://provider.com";
        ProviderSettings providerSettings = ProviderSettings.builder().issuer(issuer).build();
        ProviderContextFilter filter = new ProviderContextFilter(providerSettings);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");
        request.setServletPath("/");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        doAnswer(invocation -> {
            ProviderContext providerContext = ProviderContextHolder.getProviderContext();
            assertThat(providerContext).isNotNull();
            assertThat(providerContext.providerSettings()).isSameAs(providerSettings);
            assertThat(providerContext.getIssuer()).isEqualTo(issuer);
            return null;
        }).when(filterChain).doFilter(any(), any());

        filter.doFilter(request, response, filterChain);

        assertThat(ProviderContextHolder.getProviderContext()).isNull();
    }

    @Test
    public void doFilterWhenIssuerNotConfiguredThenResolveFromRequest() throws Exception {
        ProviderSettings providerSettings = ProviderSettings.builder().build();
        ProviderContextFilter filter = new ProviderContextFilter(providerSettings);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");
        request.setServletPath("/");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        doAnswer(invocation -> {
            ProviderContext providerContext = ProviderContextHolder.getProviderContext();
            assertThat(providerContext).isNotNull();
            assertThat(providerContext.providerSettings()).isSameAs(providerSettings);
            assertThat(providerContext.getIssuer()).isEqualTo("http://localhost");
            return null;
        }).when(filterChain).doFilter(any(), any());

        filter.doFilter(request, response, filterChain);

        assertThat(ProviderContextHolder.getProviderContext()).isNull();
    }
}
