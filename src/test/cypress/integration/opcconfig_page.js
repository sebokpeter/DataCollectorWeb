/* global cy */

describe('DataCollectorWeb - OPC config page ', () => {
    it('Navigate to the page', () => {
        cy.visit('http://localhost:8080/DataCollectorWeb/opcconfig');
    });
    
    it('Title is "OPC configuration" on page', () => {
        cy.get('title').contains('OPC configuration');
        cy.get('h1').first().contains('OPC configuration');
    });
    
    it('Has Save buttons', () => {
        cy.get('[type="submit"]').should('have.text', 'Save');
    });
    
    it('Has Back buttons', () => {
        cy.get('[href="main"]').should('have.text', 'Back');
    });
    
    it('Should not submit if data is missing', () => {
        cy.get('[type="submit"]').click().then(() => {
            cy.location('pathname').should('eq', '/DataCollectorWeb/opcconfig');
        });
    });
    
    it('Back button navigates to main', () => {
        cy.get('[href="main"]').click().then(() => {
            cy.location('pathname').should('eq', '/DataCollectorWeb/main');
        });
    });
});