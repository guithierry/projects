import { FormEvent, useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";
import Select from "react-select";
import useAuth from "../hooks/useAuth";
import { Todo, User } from "../types";
import ModalContainer from "./ModalContainer";
import Label from "./Label";
import { useLocation } from "react-router-dom";

export default function AssignUserModal({
    todo,
    visible,
    handleVisible,
}: {
    todo: Todo;
    visible: boolean;
    handleVisible(): void;
}) {
    const [users, setUsers] = useState<User[]>([]);
    const [assign, setAssign] = useState("");

    const location = useLocation();
    const projectId = location.pathname.split("/")[2];

    const { user } = useAuth();

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

    const options = users.map((u: User) => {
        return {
            value: u.id,
            label: <Label user={u} />,
        };
    });

    // function filterOptions({ value }, input: string) {
    //     if (input) {
    //         return value.email.includes(input);
    //     }

    //     return true;
    // }

    async function handleSubmit(event: FormEvent) {
        event.preventDefault();

        if (!assign) {
            return;
        }

        const response = await fetch(
            `http://localhost:8080/todos/${todo.id}/users`,
            {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${user.token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    userId: assign.value,
                }),
            }
        );

        const data = await response.json();
        // to not update the entire list in the List.tsx component
        todo.assigned = data.assigned;

        handleVisible();
    }

    return (
        <ModalContainer
            title="Assign User"
            visible={visible}
            handleVisible={handleVisible}
        >
            <Form onSubmit={handleSubmit}>
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
                    <Button variant="dark" type="submit" disabled={!assign}>
                        Assign
                    </Button>
                </div>
            </Form>
        </ModalContainer>
    );
}
