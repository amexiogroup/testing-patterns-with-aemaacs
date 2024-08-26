println "Creating tag content-fragments-testing-patterns:enabled"

propertiesNamespace = ["sling:resourceType": "cq/tagging/components/tag"]
propertiesTag = ["sling:resourceType": "cq/tagging/components/tag",
                 "jcr:title"         : "Enabled",
                 "jcr:description"   : "All the Content Fragment Models with this tag will be allowed in /content/dam/content-fragments/testing-patterns-with-aemaacs"]

aecu.contentUpgradeBuilder()
        .filterByNodeExists("/content/cq:tags")
        .forResources("/content/cq:tags")
        .filterByNodeNotExists("/content/cq:tags/content-fragments-testing-patterns")
        .doCreateResource("content-fragments-testing-patterns", "cq:Tag", propertiesNamespace)
        .run()

aecu.contentUpgradeBuilder()
        .filterByNodeExists("/content/cq:tags/content-fragments-testing-patterns")
        .forResources("/content/cq:tags/content-fragments-testing-patterns")
        .filterByNodeNotExists("/content/cq:tags/content-fragments-testing-patterns/enabled")
        .doCreateResource("enabled", "cq:Tag", propertiesTag)
        .run()

println "Updating /content/dam/content-fragments/testing-patterns-with-aemaacs with allowedModelsByTags content-fragments-testing-patterns:enabled"

aecu.contentUpgradeBuilder()
        .filterByNodeExists("/content/dam/content-fragments/testing-patterns-with-aemaacs")
        .forResources("/content/dam/content-fragments/testing-patterns-with-aemaacs")
        .doSetProperty("policy-cfm-allowedModelsByTags", (String[]) ["content-fragments-testing-patterns:enabled"], "jcr:content/policies/cfm")
        .run()
