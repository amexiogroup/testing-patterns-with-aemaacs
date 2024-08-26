/*
 *  Copyright 2021 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.amexiogroup.core.testcontext;

import com.adobe.acs.commons.models.injectors.impl.HierarchicalPagePropertyInjector;
import com.adobe.cq.wcm.core.components.internal.link.LinkManagerImpl;
import com.adobe.cq.wcm.core.components.internal.models.v3.PageImpl;
import com.adobe.cq.wcm.core.components.testing.MockHtmlLibraryManager;
import com.adobe.cq.wcm.core.components.testing.MockProductInfoProvider;
import com.adobe.granite.ui.clientlibs.ClientLibrary;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextCallback;
import io.wcm.testing.mock.wcmio.caconfig.MockCAConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.apache.sling.models.impl.via.ResourceSuperTypeViaProvider;
import org.apache.sling.models.spi.ViaProvider;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.jetbrains.annotations.NotNull;
import org.mockito.Mockito;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static io.wcm.testing.mock.wcmio.caconfig.ContextPlugins.WCMIO_CACONFIG;
import static io.wcm.testing.mock.wcmio.sling.ContextPlugins.WCMIO_SLING;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Sets up {@link AemContext} for unit tests in this application.
 */
public final class AppAemContext {

    /**
     * Custom set up rules required in all unit tests.
     */
    private static final AemContextCallback SETUP_CALLBACK = new AemContextCallback() {
        @Override
        public void execute(AemContext context) {
            // custom project initialization code for every unit test
        }
    };

    private AppAemContext() {
        // static methods only
    }

    /**
     * @return {@link AemContext}
     */
    @NotNull
    public static AemContext newAemContext() {
        return newAemContext(null);
    }

    /**
     * @return {@link AemContextBuilder}
     */
    public static AemContextBuilder newAemContextBuilder() {
        return newAemContextBuilder(ResourceResolverType.RESOURCERESOLVER_MOCK);
    }

    @NotNull
    public static AemContext newAemContext(final ResourceResolverType resourceResolverType) {
        return newAemContextBuilder(resourceResolverType).build();
    }

    /**
     * @return {@link AemContextBuilder}
     */
    @NotNull
    public static AemContextBuilder newAemContextBuilder(final ResourceResolverType resourceResolverType) {
        return new AemContextBuilder(resourceResolverType)
                .plugin(CACONFIG)
                .plugin(WCMIO_CACONFIG)
                .plugin(WCMIO_SLING)
                .plugin(CORE_COMPONENTS)
                // Since we have lots of Sling Models, we're not registering the models from the class path as this slows down the unit tests immensely
                .registerSlingModelsFromClassPath(false)
                .beforeSetUp(BEFORE_SETUP_CALLBACK)
                .afterSetUp(AFTER_SETUP_CALLBACK)
                .afterSetUp(REGISTER_CACONFIG_CONFIGS);
    }

    private static final AemContextCallback BEFORE_SETUP_CALLBACK = context -> {
        context.registerInjectActivateService(new HierarchicalPagePropertyInjector());
    };

    private static final AemContextCallback AFTER_SETUP_CALLBACK = context -> {
        context.addModelsForClasses(LinkManagerImpl.class);
    };

    public static void registerDelegate(AemContext context, Object delegate) {
        ResourceSuperTypeViaProvider resourceSuperTypeViaProvider = Mockito.mock(ResourceSuperTypeViaProvider.class);
        // Mock the ResourceSuperTypeProvider, so we can return our mocked delegate
        when(resourceSuperTypeViaProvider.getType()).thenAnswer(answer -> ResourceSuperType.class);
        when(resourceSuperTypeViaProvider.getAdaptable(any(SlingHttpServletRequest.class), eq(StringUtils.EMPTY))).thenAnswer(answer -> delegate);
        context.registerService(ViaProvider.class, resourceSuperTypeViaProvider);
    }

    private static final AemContextCallback REGISTER_CACONFIG_CONFIGS = context -> {
        MockCAConfig.contextPathStrategyAbsoluteParent(context, 1, 2);
    };

}
