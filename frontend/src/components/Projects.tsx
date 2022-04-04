import { useState, useEffect } from "react";
import { Container, Row, Col, Card, Button } from "react-bootstrap";
import { Link } from "react-router-dom";
import { Project } from "../types";
import Header from "./Header";
import ProjectFormModal from "./ProjectFormModal";

export default function Projects() {
    const [projects, setProjects] = useState<Project[]>([]);

    useEffect(() => {
        async function handleGetProjects() {
            const response = await fetch("http://localhost:8080/projects");
            const data = await response.json();
            setProjects(data);
        }

        handleGetProjects();
    }, []);

    const [projectFormModal, setProjectFormModal] = useState(false);

    return (
        <>
            <Header />

            <Container className="mt-3 mb-3">
                <Row>
                    <Col md={12}>
                        <div className="d-flex justify-content-end">
                            <Button
                                variant="outline-dark"
                                size="sm"
                                className="d-flex align-items-center"
                                onClick={() =>
                                    setProjectFormModal(!projectFormModal)
                                }
                            >
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="20"
                                    height="20"
                                    fill="currentColor"
                                    className="bi bi-plus"
                                    viewBox="0 0 16 16"
                                >
                                    <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
                                </svg>
                                New Project
                            </Button>
                        </div>
                    </Col>
                </Row>

                <Row>
                    {projects.map(({ id, name, description }) => (
                        <Col md={6} lg={4} className="mt-3" key={id}>
                            <Card body className="d-flex">
                                <Card.Title>{name}</Card.Title>

                                <Card.Text>{description}</Card.Text>

                                <Button
                                    as={Link}
                                    to={`/${id}`}
                                    variant="outline-dark"
                                    size="sm"
                                >
                                    View
                                </Button>
                            </Card>
                        </Col>
                    ))}
                </Row>

                <ProjectFormModal
                    visible={projectFormModal}
                    handleVisible={() => setProjectFormModal(!projectFormModal)}
                />
            </Container>
        </>
    );
}
