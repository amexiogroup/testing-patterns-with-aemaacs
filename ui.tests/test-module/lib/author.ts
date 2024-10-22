import {expect, Page} from "@playwright/test";
import {aem} from "lib/config";

export function gotoPage(page: Page, url: String, location: String) {
    return page.goto(`${url}${location}`,{
        waitUntil: "domcontentloaded",
        timeout: 15 * 1000
    });
}

export function goToPublisherPage(page: Page, location: String) {
    return gotoPage(page, aem.publish.base_url, location);
}

export function goToAuthorPage(page: Page, location: String, editMode: boolean = false) {
    if (editMode) {
        return gotoPage(page, aem.author.base_url, `/editor.html${location}`);
    }
    return gotoPage(page, aem.author.base_url, location);
}


export async function goToPageProperties(page: Page, location: String) {
    await goToAuthorPage(page, '/sites.html');

    if (await page.isVisible('.granite-shell-onboarding-popover')) {
        await page.locator('.granite-shell-onboarding-popover').getByRole('button', {name: 'Close'}).click();
    }

    const coralCheckboxLocator = page.locator(`[data-foundation-collection-item-id="${location}"] coral-checkbox`).first();
    const checkboxLocator = coralCheckboxLocator.locator(`[type="checkbox"]`).first();

    await checkboxLocator.waitFor();
    expect(checkboxLocator).toBeVisible();

    await coralCheckboxLocator.click({force: true});
    await checkboxLocator.check({force: true});
    expect(checkboxLocator).toBeChecked();

    await page.waitForTimeout(1000);

    await page.locator('.foundation-mode-switcher-item-liveregion', {hasText: 'Action Bar expanded'}).waitFor();
    await page.locator('[data-foundation-collection-action*="properties"]').first().click();

    return page.locator('.cq-dialog-content-page').waitFor();
}

export async function authorLogin(page: Page) {
    await authorLogout(page);

    await goToAuthorPage(page, '');

    if (page.url().includes('adobeaemcloud.com') || page.url().includes('adobeaemcloud.net')) {
        await page.locator('#coral-id-0').click()
    }

    expect(page.locator('#login')).toHaveAttribute('action', '/libs/granite/core/content/login.html/j_security_check');

    await page.fill('#username', aem.author.username);
    await page.fill('#password', aem.author.password);
    await page.click('#submit-button');

    expect(page.url()).toContain('/aem/start.html');
    await expect(page.locator('coral-shell-content')).toBeAttached();
}

export async function authorLogout(page: Page) {
    // Navigate to the AEM home page
    await goToAuthorPage(page, '');

    // Check the page title
    const pageTitle = await page.title();
    if (!pageTitle || !pageTitle.includes('AEM Sign In')) {
        // If the title doesn't contain 'AEM Sign In', navigate to the logout page
        await goToAuthorPage(page, '/system/sling/logout.html');
    }

    // Assert that the sign-in form exists
    await expect(page.locator('form[name="login"]')).toBeAttached();
}
