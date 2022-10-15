import { Notification } from "../types";
import { Button, ListGroup, OverlayTrigger, Popover } from "react-bootstrap";
import { useEffect, useState } from "react";
import useAuth from "../hooks/useAuth";

function HasNotifications({ total }: { total: number }) {
    return (
        <div
            className="position-absolute bg-primary rounded-circle d-flex align-items-center justify-content-center"
            style={{
                fontWeight: "bold",
                top: 15,
                right: 10,
                width: 18,
                height: 18,
            }}
        >
            {total}
        </div>
    );
}

// TODO: Handle realtime notifications with websocket
export default function Notifications() {
    const [notifications, setNotifications] = useState<Notification[]>([]);

    const { user } = useAuth();

    useEffect(() => {
        async function handleGetNotifications() {
            const response = await fetch(
                `http://localhost:8080/users/${user.id}/notifications`,
                {
                    headers: {
                        Authorization: `Bearer ${user.token}`,
                    },
                }
            );

            const data = await response.json();
            setNotifications(data);
        }

        handleGetNotifications();
    }, []);

    async function markAllNotificationAsRead() {
        await fetch("http://localhost:8080/notifications", {
            method: "PUT",
            headers: {
                Authorization: `Bearer ${user.token}`,
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id: user.id,
            }),
        });
    }

    const unreadNotifications = notifications.filter(
        (notification) => !notification.status
    );

    return (
        <OverlayTrigger
            trigger="click"
            placement="bottom"
            overlay={
                <Popover>
                    <Popover.Header as="h3">
                        <div className="d-flex justify-content-between align-items-center">
                            <p className="m-0">Notifications</p>

                            <p
                                className="m-0 ps-1 pe-1"
                                style={{
                                    fontSize: 12,
                                    fontWeight: "bold",
                                    cursor: "pointer",
                                }}
                                onClick={() => markAllNotificationAsRead()}
                            >
                                Mark as read
                            </p>
                        </div>
                    </Popover.Header>
                    <Popover.Body className="m-0 p-0">
                        {notifications && notifications.length > 0 && (
                            <ListGroup>
                                {notifications.map(
                                    ({ id, message, status }) => {
                                        return (
                                            <ListGroup.Item
                                                key={id}
                                                action
                                                // href=""
                                                className={`rounded-0 ${
                                                    status ?? "opacity-50"
                                                }`}
                                            >
                                                {message}
                                            </ListGroup.Item>
                                        );
                                    }
                                )}
                            </ListGroup>
                        )}

                        {notifications && notifications.length === 0 && (
                            <div>No notifications</div>
                        )}
                    </Popover.Body>
                </Popover>
            }
        >
            <div>
                <Button size="sm" variant="outline-light" className="border-0">
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="20"
                        height="20"
                        fill="currentColor"
                        className="bi bi-bell-fill"
                        viewBox="0 0 16 16"
                    >
                        <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z" />
                    </svg>

                    {unreadNotifications.length > 0 && (
                        <HasNotifications total={unreadNotifications.length} />
                    )}
                </Button>
            </div>
        </OverlayTrigger>
    );
}
