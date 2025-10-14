describe('Login Flow', () => {
  beforeEach(() => {
    cy.visit('/login'); // Assuming your login page is at /login
  });

  it('should display validation errors for empty fields', () => {
    cy.get('button[type="submit"]').click();
    cy.contains('Email/Phone is required').should('be.visible');
    cy.contains('Password is required').should('be.visible');
  });

  it('should successfully log in a user with valid credentials', () => {
    // Assuming you have a test user with these credentials
    cy.get('input[name="emailPhone"]').type('test@example.com');
    cy.get('input[name="password"]').type('password123');
    cy.get('button[type="submit"]').click();

    // Assert that the user is redirected to the dashboard or a protected page
    cy.url().should('include', '/'); // Or whatever your post-login route is
    cy.contains('Welcome, Test User').should('be.visible'); // Or some other indicator of successful login
  });

  it('should show an error for invalid credentials', () => {
    cy.get('input[name="emailPhone"]').type('wrong@example.com');
    cy.get('input[name="password"]').type('wrongpassword');
    cy.get('button[type="submit"]').click();

    cy.contains('Invalid credentials').should('be.visible'); // Or the specific error message from your backend
    cy.url().should('include', '/login'); // Should remain on the login page
  });
});
