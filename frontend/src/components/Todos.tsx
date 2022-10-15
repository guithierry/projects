import { useState, useEffect } from "react";
import { Container, Row, Col, Button, Dropdown } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import { Project, Todo, User } from "../types";
import Comments from "./Comments";
import Header from "./Header";
import List from "./List";
import TodoFormModal from "./TodoFormModal";
import ProjectUsersModal from "./ProjectUsersModal";

export default function Todos() {
    const location = useLocation();
    const projectId = location.pathname.split("/")[2];

    const [project, setProject] = useState<Project>({} as Project);
    const [todos, setTodos] = useState([]);

    const { user } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        async function handleGetProject() {
            const response = await fetch(
                `http://localhost:8080/projects/${projectId}`,
                {
                    headers: {
                        Authorization: `Bearer ${user.token}`,
                    },
                }
            );
            const data = await response.json();

            setProject(data);
        }

        handleGetProject();
    }, [projectId]);

    useEffect(() => {
        async function handleGetTodos() {
            const response = await fetch(
                `http://localhost:8080/todos/project/${projectId}`,
                {
                    headers: {
                        Authorization: `Bearer ${user.token}`,
                    },
                }
            );
            const data = await response.json();
            setTodos(data);
        }

        handleGetTodos();
    }, []);

    async function moveTodo(todo: Todo, status: string) {
        setTodos((state) => {
            const copiedState = [...state];

            copiedState.find((item: Todo) => {
                if (item.id === todo.id) {
                    item.status = status;
                }
            });

            return copiedState;
        });

        await handleTodoStatus(todo.id, status);
    }

    async function handleTodoStatus(id: string, status: string) {
        await fetch(`http://localhost:8080/todos/${id}`, {
            method: "PATCH",
            headers: {
                Authorization: `Bearer ${user.token}`,
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ status }),
        });
    }

    const [todoFormModal, setTodoFormModal] = useState(false);

    const [projectUsersModal, setProjectUsersModal] = useState(false);

    return (
        <>
            <Header />

            <Container>
                <Row>
                    <Col md={12} className="mt-3">
                        <div className="d-flex align-items-center justify-content-between">
                            <h3>{project?.name}</h3>

                            {project.owner?.id === user.id && (
                                <Dropdown>
                                    <Dropdown.Toggle
                                        variant="outline-dark"
                                        id="actions"
                                        size="sm"
                                    >
                                        <svg
                                            xmlns="http://www.w3.org/2000/svg"
                                            width="20"
                                            height="20"
                                            fill="currentColor"
                                            className="bi bi-three-dots"
                                            viewBox="0 0 16 16"
                                        >
                                            <path d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z" />
                                        </svg>
                                    </Dropdown.Toggle>

                                    <Dropdown.Menu align="end">
                                        <Dropdown.Item as={Button}>
                                            Edit
                                        </Dropdown.Item>
                                    </Dropdown.Menu>
                                </Dropdown>
                            )}
                        </div>

                        <p className="lead text-muted text-break">
                            {project?.description}
                        </p>

                        <div style={{ position: "relative" }}>
                            {project.users &&
                                project.users.length > 0 &&
                                project.users
                                    .slice(0, 6)
                                    .map((user: User, index: number) => (
                                        <div key={user.id} title={user.name}>
                                            <div
                                                className="d-flex align-items-center justify-content-center"
                                                style={{
                                                    width: 30,
                                                    height: 30,
                                                    borderLeft:
                                                        "1px solid white",
                                                    borderRadius: "50%",
                                                    color: "white",
                                                    background: "#929292",
                                                    position: "absolute",
                                                    zIndex: `${index}`,
                                                    left: `calc(${index} * 23px)`,
                                                }}
                                            >
                                                {user.name
                                                    .charAt(0)
                                                    .toUpperCase()}
                                            </div>
                                        </div>
                                    ))}

                            {project.users && project.users.length > 6 && (
                                <div
                                    title="Users"
                                    onClick={() =>
                                        setProjectUsersModal(!projectUsersModal)
                                    }
                                >
                                    <div
                                        className="d-flex align-items-center justify-content-center"
                                        style={{
                                            width: 30,
                                            height: 30,
                                            borderLeft: "1px solid white",
                                            borderRadius: "50%",
                                            color: "white",
                                            background: "#929292",
                                            position: "absolute",
                                            zIndex: 6,
                                            left: 138,
                                            cursor: "pointer",
                                        }}
                                    >
                                        <span
                                            style={{
                                                fontSize: 11,
                                                fontWeight: "bold",
                                            }}
                                        >
                                            +{project.users.length - 6}
                                        </span>
                                    </div>
                                </div>
                            )}
                        </div>
                    </Col>
                </Row>

                <div className="mb-3 d-flex justify-content-end">
                    <Button
                        variant="outline-dark"
                        size="sm"
                        className="d-flex align-items-center"
                        onClick={() => setTodoFormModal(!todoFormModal)}
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
                        New Todo
                    </Button>
                </div>

                <hr />

                <Row>
                    {[
                        { status: "todo", title: "To do", color: "#a2d2ff" },
                        {
                            status: "doing",
                            title: "In progress",
                            color: "#588157",
                        },
                        { status: "done", title: "Done", color: "#fe6d73" },
                    ].map(({ status, ...rest }) => (
                        <Col md={4} key={status}>
                            <List
                                status={status}
                                {...rest}
                                data={todos}
                                moveTodo={moveTodo}
                            />
                        </Col>
                    ))}
                </Row>

                <hr className="mt-5" />

                <Row>
                    <Col md={12}>
                        <Comments projectId={projectId} />
                    </Col>
                </Row>

                <TodoFormModal
                    projectId={projectId}
                    todos={todos}
                    setTodos={setTodos}
                    visible={todoFormModal}
                    handleVisible={() => setTodoFormModal(!todoFormModal)}
                />

                <ProjectUsersModal
                    users={project.users}
                    visible={projectUsersModal}
                    handleVisible={() =>
                        setProjectUsersModal(!projectUsersModal)
                    }
                />
            </Container>
        </>
    );
}
