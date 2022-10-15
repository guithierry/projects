import { ListGroup, Dropdown, Button } from "react-bootstrap";
import { useDrag, useDrop } from "react-dnd";
import { Todo } from "../types";
import { format } from "date-fns";
import { useState } from "react";
import AssignUserModal from "./AssignUserModal";

function Item({
    todo,
    moveTodo,
}: {
    todo: Todo;
    moveTodo(...props: any): void;
}) {
    const [{ isDragging }, dragRef] = useDrag(() => ({
        type: "ITEM",
        item: {
            todo,
        },
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
        }),
    }));

    const [assignUserModal, setAssignUserModal] = useState(false);

    return (
        <ListGroup.Item
            ref={dragRef}
            style={{
                border: isDragging ? "2px dashed gray" : "",
                opacity: todo.status.toLowerCase() === "done" ? "0.7" : "",
            }}
            variant={isDragging ? "dark" : ""}
        >
            <div className="d-flex w-100 justify-content-between">
                <h5
                    title={todo.name}
                    className="mb-1 d-inline-block text-truncate"
                    style={{ maxWidth: 325 }}
                >
                    {todo.name}
                </h5>

                <Dropdown>
                    <Dropdown.Toggle
                        variant="outline-dark"
                        id="todo-actions"
                        size="sm"
                        className="border-0"
                    >
                        <svg
                            xmlns="http://www.w3.org/2000/svg"
                            width="16"
                            height="16"
                            fill="currentColor"
                            className="bi bi-three-dots"
                            viewBox="0 0 16 16"
                        >
                            <path d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zm5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3z" />
                        </svg>
                    </Dropdown.Toggle>

                    <Dropdown.Menu align="end">
                        {["todo", "doing", "done"].map((status) => {
                            return (
                                todo.status.toLowerCase() !== status && (
                                    <Dropdown.Item
                                        key={status}
                                        as={Button}
                                        onClick={() => moveTodo(todo, status)}
                                    >
                                        {status.charAt(0).toUpperCase() +
                                            status.slice(1)}
                                    </Dropdown.Item>
                                )
                            );
                        })}
                    </Dropdown.Menu>
                </Dropdown>
            </div>

            <p className="mb-1">{todo.description}</p>

            <div className="d-flex justify-content-between align-items-center">
                <small
                    className="p-1 rounded-3 bg-dark text-light"
                    style={{
                        fontSize: 11,
                    }}
                >
                    {format(new Date(todo.createdAt), "MMM dd")}
                </small>

                {!todo.assigned && (
                    <p
                        className="mb-0 link-danger"
                        style={{
                            textDecoration: "underline",
                            cursor: "pointer",
                        }}
                        onClick={() => setAssignUserModal(!assignUserModal)}
                    >
                        Assign User
                    </p>
                )}

                {todo.assigned && (
                    <div
                        title={todo.assigned?.name}
                        className="d-flex align-items-center justify-content-center"
                        style={{
                            width: 30,
                            height: 30,
                            borderRadius: "50%",
                            color: "white",
                            background: "#929292",
                        }}
                    >
                        {todo.assigned?.name.charAt(0).toUpperCase() +
                            status.slice(1)}
                    </div>
                )}

                {assignUserModal && (
                    <AssignUserModal
                        todo={todo}
                        visible={assignUserModal}
                        handleVisible={() =>
                            setAssignUserModal(!assignUserModal)
                        }
                    />
                )}
            </div>
        </ListGroup.Item>
    );
}

export default function List({
    status,
    data,
    moveTodo,
    ...rest
}: {
    status: string;
    data: Todo[];
    moveTodo(...props: any): void;
}) {
    const [, dropRef] = useDrop(() => ({
        accept: "ITEM",
        drop(item) {
            moveTodo(item.todo, status);
        },
    }));

    const { title, color } = rest;

    return (
        <>
            <div className="d-flex align-items-center mb-2 p-2">
                <div
                    style={{
                        width: 10,
                        height: 10,
                        background: color,
                        borderRadius: 50,
                    }}
                ></div>

                <h6 className="m-0 ms-2">{title}</h6>
            </div>

            <ListGroup
                ref={dropRef}
                className="h-100 mb-3 p-2 overflow-styles"
                style={{
                    borderLeft: "1px solid rgba(0, 0, 0, 0.05)",
                    maxHeight: "450px",
                }}
            >
                {data
                    .filter((todo) => todo.status.toLowerCase() === status)
                    .map((todo: Todo) => (
                        <Item key={todo.id} todo={todo} moveTodo={moveTodo} />
                    ))}
            </ListGroup>
        </>
    );
}
