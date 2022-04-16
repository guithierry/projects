import { useState, FormEvent } from "react";
import { Form, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";

export default function SignIn({ setMode }: { setMode(): void }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const { handleAuth } = useAuth();
    const navigate = useNavigate();

    async function handleSubmit(event: FormEvent) {
        event.preventDefault();

        try {
            const response = await fetch(
                "http://localhost:8080/authentication",
                {
                    method: "POST",
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ email, password }),
                }
            );

            const data = await response.json();

            if (!response.ok) {
                throw data;
            }

            const { user, token } = data;

            handleAuth({ ...user, token });

            return navigate("/projects");
        } catch (err) {
            const { message } = err as Error;
            setError(message);
        }
    }

    return (
        <Form
            className="show-right p-3 border rounded-3"
            onSubmit={handleSubmit}
        >
            {!!error && (
                <div className="text-danger mb-3 text-center">
                    <small>{error}</small>
                </div>
            )}

            <Form.Group className="mb-3" controlId="email">
                <Form.Label>Email</Form.Label>
                <Form.Control
                    type="email"
                    placeholder="Enter email"
                    autoComplete="off"
                    value={email}
                    onChange={(event) => setEmail(event.target.value)}
                    required
                    isInvalid={!!error}
                />
            </Form.Group>

            <Form.Group className="mb-3" controlId="password">
                <Form.Label>Password</Form.Label>
                <Form.Control
                    type="password"
                    placeholder="Password"
                    autoComplete="off"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                    required
                    isInvalid={!!error}
                />
            </Form.Group>

            <Button variant="dark" type="submit" className="w-100">
                Login
            </Button>

            <Form.Group className="mt-3">
                <Form.Text className="text-muted">
                    Don't have an account?
                    <span
                        className="ms-1 text-primary"
                        style={{
                            cursor: "pointer",
                        }}
                        onClick={() => setMode()}
                    >
                        Register
                    </span>
                </Form.Text>
            </Form.Group>
        </Form>
    );
}
