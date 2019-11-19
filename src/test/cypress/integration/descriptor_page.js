/* global cy, expect */

describe('DataCollectorWeb - Descriptor page ', () => {
    it('Navigate to the page', () => {
        cy.visit('http://localhost:8080/DataCollectorWeb/descriptor?set_number=1');
    });
    
    it('Title is "Create descriptor" on page', () => {
        cy.get('title').contains('Create descriptor');
        cy.get('h1').first().contains('Create descriptor');
    });
    
    it('Has 2 buttons', () => {
        cy.get('.btn').should('have.length', 2); 
    });
    
    it('Has Save buttons', () => {
        cy.get('[type="submit"]').should('have.text', 'Save');
    });
    
    it('Has Back buttons', () => {
        cy.get('[href="sqlconfig"]').should('have.text', 'Back');
    });
    
    it('1 entry genetarion works', () => {
        cy.get('h3').should('contain.text', 'Entry');
    });
    
    it('Multiple entry genetarion works', () => {
        cy.visit('http://localhost:8080/DataCollectorWeb/descriptor?set_number=5');
        cy.get('h3').should((h3) => {
            expect(h3).to.have.lengthOf(5);
            for (var i = 0; i < h3.length; i++) {
                expect(h3[i]).to.contain('Entry');
            }
        });
    });
    
    it('Should not submit if data is missing', () => {
        cy.get('[type="submit"]').click().then(() => {
            cy.location('pathname').should('eq', '/DataCollectorWeb/descriptor');
        });
    });
    
    it('Back button navigates to sql config', () => {
        cy.get('[href="sqlconfig"]').click().then(() => {
            cy.location('pathname').should('eq', '/DataCollectorWeb/sqlconfig');
        });
    });
});