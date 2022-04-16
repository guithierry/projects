import { useState } from "react";
import { Container, Row, Col } from "react-bootstrap";

import SignIn from "./SignIn";
import SignUp from "./SignUp";

type Mode = "signin" | "signup";

export default function Authentication() {
    const [mode, setMode] = useState<Mode>("signin");

    return (
        <Container>
            <Row className="vh-100 d-flex align-items-center justify-content-center">
                <Col lg={4} md={6}>
                    {mode === "signin" ? (
                        <SignIn setMode={() => setMode("signup")} />
                    ) : (
                        <SignUp setMode={() => setMode("signin")} />
                    )}
                </Col>
            </Row>
        </Container>
    );
}
