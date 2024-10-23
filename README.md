# AEM Project Reference - README

This project serves as a reference for all changes made to the AEM archetype, showcasing best practices and customizations that enhance the development and testing experience for AEM as a Cloud Service (AEMaaCS).

## Project Overview

This reference project is based on the AEM archetype but has been extended to demonstrate various modifications and improvements aimed at optimizing workflows, testing, and deployment for AEMaaCS environments. The changes include custom modules, testing enhancements, and integration with CI/CD tools.

## Key Changes from the AEM Archetype

### 1. Custom Modules
- **`ui.content` Module**: Modified to use the 'replace' filter mode instead of 'merge' to ensure consistent content configuration across all environments. Editable templates are locked to maintain uniformity and prevent manual changes.
- **`it.content` Module**: Added a dedicated testing module for creating isolated test pages for each component and template, which is used for integration tests and other targeted testing scenarios.

### 2. Enhanced Testing Suite
- **Integration Tests**: Integration tests now run both locally and in the Remote Development Environment (RDE), ensuring consistent functionality across multiple stages.
- **UI Tests**: Utilizes Playwright for fast, multi-browser UI testing with built-in support for accessibility checks.
- **Lighthouse Tests**: Added to evaluate performance, accessibility, and SEO metrics for each component in real-world conditions.

### 3. CI/CD Integration
- **Static Code Analysis**: Tools like SpotBugs, PMD, and Checkstyle are used to catch code quality issues early, avoiding disruptions in the Cloud Manager pipeline.
- **Architecture Rules Testing**: ArchUnit is integrated with the CI/CD pipeline to ensure that architectural consistency is maintained as the project evolves.
- **Code Coverage**: JaCoCo is used to enforce code coverage requirements, ensuring each part of the codebase is well-tested before deployment.

## How to Use This Reference
- **Development Setup**: Clone the repository and set up your local development environment using AEM SDK. Ensure that both `ui.content` and `it.content` modules are deployed locally for a complete testing setup.
- **Testing**: Run the provided unit, integration, and UI tests to validate changes during development. Use the included Lighthouse tests to evaluate page performance and accessibility.
- **CI/CD Pipeline**: Follow the examples provided to integrate your CI/CD pipeline with Cloud Manager or other tools. Static analysis, architecture validation, and coverage checks are all automated to maintain code quality.

## Important Notes
- This reference project emphasizes maintaining clean and consistent environments across all stages: local, RDE, and DEV. All changes should follow the guidelines demonstrated here to ensure quality and stability.

## Contributing
If you find any issues or have suggestions for further improvements, feel free to open a pull request or create an issue.

## License
This project is licensed under the MIT License. See the `LICENSE` file for more details.

