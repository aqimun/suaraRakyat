import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

export const ProjectContext = createContext();

export const ProjectProvider = ({ children }) => {
    const [projects, setProjects] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchProjects = async () => {
            try {
                const response = await axios.get('/api/projects');
                setProjects(response.data);
                setLoading(false);
            } catch (err) {
                setError(err);
                setLoading(false);
            }
        };

        fetchProjects();
    }, []);

    return (
        <ProjectContext.Provider value={{ projects, loading, error }}>
            {children}
        </ProjectContext.Provider>
    );
};
