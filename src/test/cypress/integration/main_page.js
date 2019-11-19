/* global cy, expect */

describe('DataCollectorWeb - Main page', () => {
    it('Navigate to the page', () => {
        cy.visit('http://localhost:8080/DataCollectorWeb/');
    });
    
    it('Title is "Seacon DataCollector" on page', () => {
        cy.get('title').contains('Seacon DataCollector');
        cy.get('h1').first().contains('Seacon DataCollector');
    });
    
    it('Has 2 or 3 data tables', () => {
        cy.get('table').should('have.length.least', 2);
        cy.get('table').should('have.length.most', 3);
    });
    
    it('Every table row has "Delete" button', () => {
        cy.get('tbody').children('tr').each((row) => {
            var child = row.children().last().children()[0];
            if (child === 'button') {
                expect(child).to.have.text('Delete');
                expect(child).to.have.prop('name').contains('delete');
            } else if (child === 'a') {
                expect(child).to.have.text('+');
            }
        });
    });
    
    it('Has Start button', () => {
        expect(cy.get('[name="startButton"]')).to.exist;
    });
    
    it('SQL add button redirects to the right component', () => {
        cy.visit('http://localhost:8080/DataCollectorWeb/');
        cy.get('[href="sqlconfig"]').should('have.class', 'btn').click().then(() => {
            cy.location('pathname').should('eq', '/DataCollectorWeb/sqlconfig');
        });
    });
    
    it('OPC add button redirects to the right component', () => {
        cy.visit('http://localhost:8080/DataCollectorWeb/');
        cy.get('[href="opcconfig"]').should('have.class', 'btn').click().then(() => {
            cy.location('pathname').should('eq', '/DataCollectorWeb/opcconfig');
        });
    });
});
