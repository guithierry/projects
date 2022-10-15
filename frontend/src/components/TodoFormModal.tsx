import { Form, Button } from "react-bootstrap";
import { FormEvent, useEffect, useState } from "react";
import ModalContainer from "./ModalContainer";
import { Todo, User } from "../types";
import useAuth from "../hooks/useAuth";
import Select from "react-select";
import Label from "./Label";

export default function TodoFormModal({
    projectId,
    todos,
    setTodos,
    visible,
    handleVisible,
}: {
    projectId: string;
    todos: Todo[];
    setTodos(props: any): void;
    visible: boolean;
    handleVisible(): void;
}) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const { user } = useAuth();

    const [users, setUsers] = useState<User[]>([]);

    useEffect(() => {
        async function getProjectUsers() {
            const response = await fetch(
                `http://localhost:8080/projects/${projectId}/users`,
                {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${user.token}`,
                        "Content-Type": "application/json",
                    },
                }
            );

            const data = await response.json();
            setUsers(data);
        }

        getProjectUsers();
    }, []);

    const [assign, setAssign] = useState("");

    const options = users.map((u: User) => {
        return {
            value: u.id,
            label: <Label user={u} />,
        };
    });

    async function handleSubmit(event: FormEvent) {
        event.preventDefault();

        if (!name || !description) {
            return;
        }

        const response = await fetch(`http://localhost:8080/todos`, {
            method: "POST",
            headers: {
                Authorization: `Bearer ${user.token}`,
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name,
                description,
                projectId,
                userId: assign.value,
            }),
        });
        const data = await response.json();

        setTodos([...todos, data]);
        handleVisible();

        setName("");
        setDescription("");
    }

    return (
        <ModalContainer
            title="Create Todo"
            visible={visible}
            handleVisible={handleVisible}
        >
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mt-3" controlId="todo-name">
                    <Form.Label>Name</Form.Label>

                    <Form.Control
                        value={name}
                        onChange={(event) => setName(event.target.value)}
                        autoComplete="off"
                    />
                </Form.Group>

                <Form.Group className="mt-3" controlId="todo-description">
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

                <Form.Group className="mt-3" controlId="assign-todo">
                    <Form.Label>Assign a user to todo</Form.Label>
                    <Select
                        placeholder="Search user by email"
                        options={options}
                        // isMulti
                        onChange={(e) => setAssign(e)}
                        isSearchable
                        // filterOption={filterOptions}
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
