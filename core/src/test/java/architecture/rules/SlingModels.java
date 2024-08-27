package architecture.rules;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.PackageMatcher;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.apache.sling.models.annotations.Model;
import org.osgi.annotation.versioning.Version;

import javax.inject.Inject;
import java.util.stream.Collectors;

import static architecture.rules.Servlets.SERVLETS_PACKAGE;
import static com.tngtech.archunit.base.DescribedPredicate.equalTo;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AnalyzeClasses(packages = SlingModels.PACKAGE_PREFIX)
public class SlingModels {

    static final String PACKAGE_PREFIX = "com.amexiogroup";
    static final String MODELS_PACKAGE = "..models..";
    static final String IMPL_PACKAGE = "..impl..";

    @ArchTest
    private static final ArchRule slingModelsHaveNoGenericInjectAnnotation = fields()
            .that()
            .areDeclaredInClassesThat(are(annotatedWith(Model.class)))
            .and()
            .areAnnotatedWith(Inject.class)
            .should()
            .containNumberOfElements(equalTo(0))
            .allowEmptyShould(true);

    @ArchTest
    void packagesOfSlingModelsShouldBeAnnotated(JavaClasses classes) {
        var violations = classes
                .getDefaultPackage()
                .getSubpackagesInTree()
                .stream()
                .filter(pkg -> pkg.getClasses()
                        .stream()
                        .anyMatch(clazz ->
                                clazz.isAnnotatedWith(Model.class)
                                        && PackageMatcher.of(PACKAGE_PREFIX + MODELS_PACKAGE).matches(clazz.getPackageName())
                        )
                )
                .filter(pkg -> !pkg.isAnnotatedWith(Version.class))
                .map(pkg -> pkg.getDescription() + " is not annotated with @" + Version.class.getSimpleName())
                .collect(Collectors.toList());
        assertEquals(0, violations.size(), violations.toString());
    }
    
}
