import React, { useContext } from 'react';
import { ProjectContext } from '../../context/ProjectContext.jsx';
import '../../styles/ProjectPage.css';

const ProjectPage = () => {
    const { projects, loading, error } = useContext(ProjectContext);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className="project-page">
            <h1>Projects</h1>
            <div className="project-list">
                {projects.map((project) => (
                    <div key={project.id} className="project-card">
                        <h2>{project.name}</h2>
                        <p>{project.description}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ProjectPage;
