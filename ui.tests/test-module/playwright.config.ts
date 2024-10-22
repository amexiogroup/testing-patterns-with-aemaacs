// @ts-check
const {defineConfig, devices} = require('@playwright/test');

/**
 * Read environment variables from file.
 * https://github.com/motdotla/dotenv
 */
// require('dotenv').config();

let reportsPath = process.env.REPORTS_PATH || 'playwright-report/results.xml';
if (!reportsPath.endsWith('.xml')) {
    reportsPath += '/playwright-results.xml';
}
export const reports_path = reportsPath;

/**
 * @see https://playwright.dev/docs/test-configuration
 */
module.exports = defineConfig({
    testDir: './e2e',
    /* Maximum time one test can run for. */
    timeout: 60 * 1000,
    /* Run tests in files in parallel */
    fullyParallel: true,
    /* Fail the build on CI if you accidentally left test.only in the source code. */
    forbidOnly: !!process.env.CI,
    /* Retry on CI only */
    retries: process.env.CI ? 2 : 0,
    /* Opt out of parallel tests on CI. */
    workers: process.env.CI ? 1 : undefined,
    /* Reporter to use. See https://playwright.dev/docs/test-reporters */
    reporter: [
        ['list'],
        ['html', {open: 'never'}],
        ['junit', {outputFile: reports_path}]
    ],
    /* Shared settings for all the projects below. See https://playwright.dev/docs/api/class-testoptions. */
    use: {
        /* Base URL to use in actions like `await page.goto('/')`. */
        // baseURL: 'http://127.0.0.1:3000',

        /* Collect trace when retrying the failed test. See https://playwright.dev/docs/trace-viewer */
        trace: 'on-first-retry',
    },

    /* Configure projects for major browsers */
    projects: [
        {
            name: 'sanity',
            testMatch: '**/sanity/**',
            use: {...devices['Desktop Chrome']},
            retries: 2,
        },

        {
            name: 'chromium',
            testIgnore: '**/sanity/**',
            use: {...devices['Desktop Chrome']},
            retries: 1,
        },

        {
            name: 'firefox',
            testIgnore: '**/sanity/**',
            use: {...devices['Desktop Firefox']},
            retries: 2,
        },

        {
            name: 'webkit',
            testIgnore: '**/sanity/**',
            use: {...devices['Desktop Safari']},
            retries: 1,
        },

        /* Test against mobile viewports. */
        // {
        //   name: 'Mobile Chrome',
        //   use: { ...devices['Pixel 5'] },
        // },
        // {
        //   name: 'Mobile Safari',
        //   use: { ...devices['iPhone 12'] },
        // },

        /* Test against branded browsers. */
        // {
        //   name: 'Microsoft Edge',
        //   use: { ...devices['Desktop Edge'], channel: 'msedge' },
        // },
        // {
        //   name: 'Google Chrome',
        //   use: { ...devices['Desktop Chrome'], channel: 'chrome' },
        // },
    ],
});

