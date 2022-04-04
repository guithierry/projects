import { ListGroup } from "react-bootstrap";
import { useDrag, useDrop } from "react-dnd";
import { Todo } from "../types";
import { format } from "date-fns";

function Item({ todo }: { todo: Todo }) {
    const [{ isDragging }, dragRef] = useDrag(() => ({
        type: "ITEM",
        item: {
            todo,
        },
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
        }),
    }));

    return (
        <ListGroup.Item
            ref={dragRef}
            style={{
                border: isDragging ? "2px dashed gray" : "",
                opacity:
                    todo.status.toLocaleLowerCase() === "done" ? "0.7" : "",
            }}
            variant={isDragging ? "dark" : ""}
        >
            <div className="d-flex w-100 justify-content-between">
                <h5 className="mb-1">{todo.name}</h5>
            </div>

            <p className="mb-1">{todo.description}</p>

            <small
                className="p-1 rounded-3 bg-dark text-light"
                style={{
                    fontSize: 11,
                }}
            >
                {format(new Date(todo.createdAt), "MMM dd")}
            </small>
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
                        <Item key={todo.id} todo={todo} />
                    ))}
            </ListGroup>
        </>
    );
}
