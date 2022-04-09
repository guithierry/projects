import { useState, useEffect, FormEvent } from "react";
import { Form, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import ModalContainer from "./ModalContainer";
import Select from "react-select";
import { User } from "../types";

export default function ProjectFormModal({
    visible,
    handleVisible,
}: {
    visible: boolean;
    handleVisible(): void;
}) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [assigns, setAssigns] = useState([]);
    const navigate = useNavigate();

    const [users, setUsers] = useState([]);

    useEffect(() => {
        async function getUsers() {
            const response = await fetch("http://localhost:8080/users");
            const data = await response.json();

            setUsers(data);
        }

        getUsers();
    }, []);

    const options = users.map((user: User) => {
        return {
            value: user.id,
            label: (
                <div className="d-flex align-items-center" key={user.id}>
                    {user.picture ? (
                        <img
                            src={user.picture}
                            style={{
                                maxWidth: 20,
                                maxHeight: 20,
                                borderRadius: "50%",
                            }}
                        />
                    ) : (
                        <div
                            className="d-flex align-items-center justify-content-center text-light"
                            style={{
                                width: 20,
                                height: 20,
                                borderRadius: "50%",
                                background: "#929292",
                            }}
                        >
                            {user.name.charAt(0).toUpperCase()}
                        </div>
                    )}
                    <span className="ms-3 text-truncate">
                        {user.name} - {user.email}
                    </span>
                </div>
            ),
        };
    });

    function filterOptions({ value }, input: string) {
        if (input) {
            return value.email.includes(input);
        }

        return true;
    }

    async function handleSubmit(event: FormEvent) {
        event.preventDefault();

        if (!name || !description) {
            return;
        }

        const assignedUsers = assigns.map((assign) => ({
            id: assign.value,
        }));

        const response = await fetch("http://localhost:8080/projects", {
            method: "POST",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name, description, users: assignedUsers }),
        });
        const data = await response.json();

        setName("");
        setDescription("");

        return navigate(`/${data.id}`);
    }

    return (
        <ModalContainer
            title="Create Project"
            visible={visible}
            handleVisible={handleVisible}
        >
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mt-3" controlId="project-name">
                    <Form.Label>Name</Form.Label>

                    <Form.Control
                        value={name}
                        onChange={(event) => setName(event.target.value)}
                        autoComplete="off"
                    />
                </Form.Group>

                <Form.Group className="mt-3" controlId="project-description">
                    <Form.Label>Description</Form.Label>

                    <Form.Control
                        value={description}
                        onChange={(event) => setDescription(event.target.value)}
                        as="textarea"
                        rows={4}
                        style={{
                            resize: "none",
                        }}
                        autoComplete="off"
                    />
                </Form.Group>

                <Form.Group className="mt-3" controlId="project-description">
                    <Form.Label>Assign Users</Form.Label>
                    <Select
                        placeholder="Assign users to the new project"
                        options={options}
                        isMulti
                        onChange={(e) => setAssigns(e)}
                        isSearchable
                        filterOption={filterOptions}
                    />
                </Form.Group>

                <div className="mt-3 d-flex justify-content-end">
                    <Button
                        variant="dark"
                        type="submit"
                        disabled={!name || !description}
                    >
                        Create
                    </Button>
                </div>
            </Form>
        </ModalContainer>
    );
}
