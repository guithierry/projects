import { useState, useEffect, FormEvent } from "react";
import { Form, Button, ListGroup } from "react-bootstrap";
import { Comment } from "../types";
import Pagination from "./Pagination";
import { dateFormat } from "../utils/dateFormat";
import useAuth from "../hooks/useAuth";

interface PaginationProps {
    totalPages: number;
    number: number;
}

export default function Comments({ projectId }: { projectId: string }) {
    const [comments, setComments] = useState<Comment[]>([]);
    const [pageable, setPageble] = useState<PaginationProps>({
        number: 0,
        totalPages: 0,
    });
    const { user } = useAuth();

    useEffect(() => {
        async function getComments() {
            const response = await fetch(
                `http://localhost:8080/comments/${projectId}/?page=${pageable.number}`,
                {
                    headers: {
                        Authorization: `Bearer ${user.token}`,
                    },
                }
            );
            const data = await response.json();

            setPageble({
                totalPages: data.totalPages,
                number: data.number,
            });

            setComments(data.content);
        }

        getComments();
    }, [pageable.number]);

    function handlePage(page: number) {
        setPageble({ ...pageable, number: page });
    }

    const [description, setDescription] = useState("");

    async function handleCreateComment(event: FormEvent) {
        event.preventDefault();

        if (!description) {
            return;
        }

        const response = await fetch("http://localhost:8080/comments", {
            method: "POST",
            headers: {
                Authorization: `Bearer ${user.token}`,
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ description, projectId, userId: user.id }),
        });
        const data = await response.json();

        setComments((state) => {
            const copiedState = [...state];
            copiedState.splice(0, 0, data);

            return copiedState;
        });

        setDescription("");
    }

    return (
        <div className="mb-3">
            <h4>Comments</h4>

            <Form onSubmit={handleCreateComment}>
                <Form.Group className="mt-3" controlId="comment-description">
                    <Form.Control
                        value={description}
                        onChange={(event) => setDescription(event.target.value)}
                        as="textarea"
                        rows={4}
                        style={{
                            resize: "none",
                        }}
                        autoComplete="off"
                        placeholder="Text here..."
                    />

                    <div className="mt-3 d-flex justify-content-end">
                        <Button
                            variant="outline-dark"
                            type="submit"
                            disabled={!description}
                        >
                            Comment
                        </Button>
                    </div>
                </Form.Group>
            </Form>

            <ListGroup className="mt-3 mb-3">
                {comments.map(
                    ({ id, description, user: commentUser, createdAt }) => (
                        <ListGroup.Item className="d-flex" key={id}>
                            <div
                                className="d-flex justify-content-center align-items-center fw-bold text-dark"
                                style={{
                                    minWidth: 50,
                                    maxHeight: 50,
                                    borderRadius: "50%",
                                    background: "#e0e0e0",
                                }}
                            >
                                {user.name.charAt(0).toUpperCase()}
                            </div>

                            <div className="w-100 ps-3">
                                <div className="mt-2 d-flex justify-content-between">
                                    <h6 className="m-0">{commentUser.name}</h6>
                                    <small
                                        className="text-muted fw-bold"
                                        style={{
                                            fontSize: 11,
                                        }}
                                    >
                                        {dateFormat(createdAt)}
                                    </small>
                                </div>

                                <p className="text-justify text-break">
                                    {description}
                                </p>
                            </div>
                        </ListGroup.Item>
                    )
                )}
            </ListGroup>

            <Pagination
                totalPages={pageable.totalPages}
                current={pageable.number}
                setCurrent={handlePage}
            />
        </div>
    );
}
