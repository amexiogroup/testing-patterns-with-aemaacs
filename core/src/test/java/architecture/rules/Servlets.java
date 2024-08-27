package architecture.rules;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.osgi.annotation.versioning.Version;
import org.osgi.service.component.annotations.Component;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = "com.amexiogroup", importOptions = {ImportOption.DoNotIncludeTests.class})
public class Servlets {

    static final String SERVLETS_PACKAGE = "..servlets..";

    @ArchTest
    private static final ArchRule allServletsAreAnnotated = classes()
            .that()
            .resideInAPackage(SERVLETS_PACKAGE)
            .and()
            .haveSimpleNameNotEndingWith("Dto")
            .and()
            .areNotAnnotatedWith(Version.class)
            .and()
            .areNotNestedClasses()
            .and()
            .areNotInterfaces()
            .should()
            .beAnnotatedWith(Component.class);

    @ArchTest
    private static final ArchRule allServletsHaveCorrectNameSuffix = classes()
            .that()
            .resideInAPackage(SERVLETS_PACKAGE)
            .and()
            .areNotAnnotatedWith(Version.class)
            .and()
            .areNotNestedClasses()
            .should()
            .haveSimpleNameEndingWith("Servlet");

}
