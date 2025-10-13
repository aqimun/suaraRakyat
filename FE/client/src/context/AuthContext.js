import React, { createContext, useState, useContext } from 'react';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false); // Default to false
  const [user, setUser] = useState(null); // Store user data

  // Placeholder for login function
  const login = (userData) => {
    setIsLoggedIn(true);
    setUser(userData);
    // In a real app, this would involve API calls and token storage
  };

  // Placeholder for logout function
  const logout = () => {
    setIsLoggedIn(false);
    setUser(null);
    // In a real app, this would involve clearing tokens
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};
