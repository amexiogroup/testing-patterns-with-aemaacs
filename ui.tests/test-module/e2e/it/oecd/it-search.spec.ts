import {expect, test} from '@playwright/test';
import {aem} from '../../../lib/config.js';

const globalSearchPageUrl: string = '/content/oecd/en/pages/search.html';
const facetedSearchPageUrl: string = '/content/oecd/en/pages/search/events.html';

test('Test global search results page without searchterm', async ({page}) => {
    await page.goto(aem.publish.base_url + globalSearchPageUrl);

    // SearchBox container should be visible but empty
    const searchBoxContainer = page.locator('#oecd-searchbox');
    await expect(searchBoxContainer).toBeVisible();
    await expect(searchBoxContainer.locator('input[type="search"]')).toHaveValue('');

    // Search Results container should be visible and having the text "No results found"
    const searchResultsContainer = page.locator('#oecd-global-search-results');
    await expect(searchResultsContainer).toBeVisible();
    await expect(searchResultsContainer).toHaveText('No results found');
});

test('Test global search results page with searchterm', async ({page}) => {
    const searchTerm: string = 'test';
    await page.goto(aem.publish.base_url + globalSearchPageUrl + `?q=${searchTerm}`);

    // SearchBox container should be visible but empty
    const searchBoxContainer = page.locator('#oecd-searchbox');
    await expect(searchBoxContainer).toBeVisible();
    await expect(searchBoxContainer.locator('input[type="search"]')).toHaveValue(searchTerm);

    // Search Results container should be visible and having the text "X results in English"
    const searchResultsContainer = page.locator('#oecd-global-search-results');
    await expect(searchResultsContainer).toBeVisible();
    await expect(searchResultsContainer.locator('.cmp-search-results__result-count')).not.toHaveText('No results in English');
    await expect(searchResultsContainer.locator('.cmp-search-results__result-count')).toContainText('results in English');

    // Search Results List should be visible
    await expect(searchResultsContainer.locator('.cmp-list')).toBeVisible();

    // Search Results Pagination should be visible
    await expect(searchResultsContainer.locator('.cmp-pagination')).toBeVisible();
});

test('Test faceted search results page without searchterm', async ({page}) => {
    await page.goto(aem.publish.base_url + globalSearchPageUrl);

    // SearchBox container should be visible but empty
    const searchBoxContainer = page.locator('#oecd-searchbox');
    await expect(searchBoxContainer).toBeVisible();
    await expect(searchBoxContainer.locator('input[type="search"]')).toHaveValue('');

    // Search Results container should be visible and having the text "No results found"
    const searchResultsContainer = page.locator('#oecd-faceted-search-results');
    await expect(searchResultsContainer).toBeVisible();
    await expect(searchResultsContainer).not.toHaveText('No results found');

    // Search Facets should be visible
    await expect(page.locator('#oecd-search-facet-rail')).toBeVisible();
});

test('Test faceted search results page with searchterm', async ({page}) => {
    const searchTerm: string = 'test';
    await page.goto(aem.publish.base_url + facetedSearchPageUrl + `?q=${searchTerm}`);

    // SearchBox container should be visible but empty
    const searchBoxContainer = page.locator('#oecd-searchbox');
    await expect(searchBoxContainer).toBeVisible();
    await expect(searchBoxContainer.locator('input[type="search"]')).toHaveValue(searchTerm);

    // Search Results container should be visible and having the text "X results"
    const searchResultsContainer = page.locator('#oecd-faceted-search-results');
    await expect(searchResultsContainer).toBeVisible();
    await expect(searchResultsContainer.locator('.cmp-search-results__result-count')).not.toHaveText('No results');
    await expect(searchResultsContainer.locator('.cmp-search-results__result-count')).toContainText('results');

    // Search Results List should be visible
    await expect(searchResultsContainer.locator('.cmp-list')).toBeVisible();

    // Search Results Pagination should be visible
    await expect(searchResultsContainer.locator('.cmp-pagination')).toBeVisible();

    // Search Facets should be visible
    await expect(page.locator('#oecd-search-facet-rail')).toBeVisible();
});
