import {expect, Page, test} from '@playwright/test';
import {authorLogin, goToAuthorPage, goToPageProperties} from "lib/author";
import {aem} from "lib/config";

const AEM_SAMPLE_PAGE = '/content/oecd';

test.beforeEach(async ({page}) => {
    await authorLogin(page);
});

test('should be possible to display Solutions panel', async ({page}) => {
    await goToAuthorPage(page, '');

    // Click on the Solutions panel toggle
    await page.click('[data-foundation-toggleable-control-src$="solutionswitcher.html"]');

    // Check if the Solutions panel exists
    await expect(page.locator('coral-shell-menu[aria-label$="solutions"]')).toBeVisible();
});

const changePageTitle = async (page: Page, modifiedTitle: string) => {
    // Change page title
    await goToPageProperties(page, `${AEM_SAMPLE_PAGE}`);

    // Get original title
    const originalTitle = await page.inputValue('[name="./jcr:title"]');

    // Modify title
    await page.fill('[name="./jcr:title"]', modifiedTitle);

    // Submit
    await page.click('[type="submit"]');

    // Wait until we get redirected to the Sites console
    await page.locator(`[data-foundation-collection-item-id="${AEM_SAMPLE_PAGE}"] [type="checkbox"]`).waitFor();

    // Navigate to the modified page
    await goToAuthorPage(page, `${AEM_SAMPLE_PAGE}.html`);

    return originalTitle;
};

test.describe('AEM Page Properties Modifications', async () => {
    let originalTitle = 'OECD';

    test.afterEach(async ({page}) => {
        // Reset page title
        await changePageTitle(page, originalTitle);

        // Assert title is the original one
        expect(await page.title()).toContain(originalTitle);
    });
});
