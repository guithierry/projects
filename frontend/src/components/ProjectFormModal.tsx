import { useState, FormEvent } from "react";
import { Form, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import ModalContainer from "./ModalContainer";

export default function ProjectFormModal({
    visible,
    handleVisible,
}: {
    visible: boolean;
    handleVisible(): void;
}) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");

    const navigate = useNavigate();

    async function handleSubmit(event: FormEvent) {
        event.preventDefault();

        if (!name || !description) {
            return;
        }

        const response = await fetch("http://localhost:8080/projects", {
            method: "POST",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name, description }),
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
