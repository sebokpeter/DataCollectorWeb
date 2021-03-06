/* global cy */

describe('DataCollectorWeb - SQL config page ', () => {
    it('Navigate to the page', () => {
        cy.visit('http://localhost:8080/DataCollectorWeb/sqlconfig');
    });
    
    it('Title is "SQL configuration" on page', () => {
        cy.get('title').contains('SQL configuration');
        cy.get('h1').first().contains('SQL server parameter configuration');
    });
    
    it('Has Save buttons', () => {
        cy.get('[type="submit"]').should('have.text', 'Save');
    });
    
    it('Has Back buttons', () => {
        cy.get('[href="main"]').should('have.text', 'Back');
    });
    
    it('Should not submit if data is missing', () => {
        cy.get('[type="submit"]').click().then(() => {
            cy.location('pathname').should('eq', '/DataCollectorWeb/sqlconfig');
        });
    });
    
    it('Back button navigates to main', () => {
        cy.get('[href="main"]').click().then(() => {
            cy.location('pathname').should('eq', '/DataCollectorWeb/main');
        });
    });
});
