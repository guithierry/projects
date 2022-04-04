import { Form, Button } from "react-bootstrap";
import { FormEvent, useState } from "react";
import ModalContainer from "./ModalContainer";
import { Todo } from "../types";

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

    async function handleSubmit(event: FormEvent) {
        event.preventDefault();

        if (!name || !description) {
            return;
        }

        const response = await fetch(`http://localhost:8080/todos`, {
            method: "POST",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name, description, projectId }),
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
