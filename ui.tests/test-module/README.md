# Playwright Test Module

This module is designed for testing the UI and utilizes the **Playwright** framework to enable reliable end-to-end
testing.

Some sanity tests of basic tasks like logging in-out of AEM instances, adding an asset, changing a page's title, ... are
included.

## Integration UI Tests

All tests that should be executed on every pull request build should be included as spec file complying
to `specs/it/**/*.js`.

These tests can use the content coming from the `it.content` module and won't run on the Stage / Production Pipeline

## Sanity Tests

All tests that will only be executed on the Production Pipeline should be included as spec file complying
to `specs/sanity/**/*.js` package.

These tests do not have any content installed for them. If content is required to make the test succeed, the test itself
is responsible for creating AND deleting the content

## Usage

### Local testing

- Install dependencies
  ```shell
  npm install
  ```

- Set environment variables required for test execution
  ```shell
  export AEM_AUTHOR_URL=https://author-p120157-e1174191.adobeaemcloud.com 
  export AEM_AUTHOR_USERNAME=admin
  export AEM_AUTHOR_PASSWORD=***
  export AEM_PUBLISH_URL=https://publish-p120157-e1174191.adobeaemcloud.com
  export AEM_PUBLISH_USERNAME=admin
  export AEM_PUBLISH_PASSWORD=***
  ```

- Run tests with one of the following commands
  ```shell
  npm test                # Using all projects and configured browsers
  npm run test:sanity     # Running only the sanity tests
  npm run test:chromium   # Using Only Chromium browser
  npm run test:firefox    # Using Only Firefox browser
  npm run test:webkit     # Using Only Webkit browser
  ```

- For debugging a particular test, you may run Playwright with the browser visible
  ```shell
  npx playwright test e2e/locationOfTheTest.ts -g 'The Title of Test you want to run' --debug
  ```

After execution, reports are available in `playwright-report` folder.
