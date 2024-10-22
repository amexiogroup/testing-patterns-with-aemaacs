// AEM Author
let aem_author_basel_url = process.env.AEM_AUTHOR_URL || 'http://localhost:4502';
let aem_author_username = process.env.AEM_AUTHOR_USERNAME || 'admin';
let aem_author_password = process.env.AEM_AUTHOR_PASSWORD || 'admin';
// AEM Publish
let aem_publish_basel_url = process.env.AEM_PUBLISH_URL ||'http://localhost:4503';
let aem_publish_username = process.env.AEM_PUBLISH_USERNAME || 'admin';
let aem_publish_password = process.env.AEM_PUBLISH_PASSWORD || 'admin';

export const aem = {
    author: {
        base_url: aem_author_basel_url,
        username: aem_author_username,
        password: aem_author_password,
    },
    publish: {
        base_url: aem_publish_basel_url,
        username: aem_publish_username,
        password: aem_publish_password,
    }
};
