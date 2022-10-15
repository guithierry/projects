import { User } from "../types";

export default function Label({ user }: { user: User }) {
    return (
        <div className="d-flex align-items-center" key={user.id}>
            <div
                className="d-flex align-items-center justify-content-center text-light"
                style={{
                    width: 20,
                    height: 20,
                    borderRadius: "50%",
                    background: "#929292",
                }}
            >
                {user.name.charAt(0).toUpperCase()}
            </div>
            <span className="ms-3 text-truncate">
                {user.name} - {user.email}
            </span>
        </div>
    );
}
