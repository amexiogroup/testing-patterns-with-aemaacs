package architecture.rules;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.amexiogroup")
public class Configs {

    @ArchTest
    private static final ArchRule allConfigInterfacesHaveCorrectNameSuffix = classes()
            .that()
            .areAnnotations()
            .should()
            .haveSimpleNameEndingWith("Config");

}
