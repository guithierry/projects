import { Link } from "react-router-dom";
import Notifications from "./Notifications";

export default function Header() {
    return (
        <header className="p-3 bg-dark d-flex justify-content-between align-items-center">
            <Link
                to="/projects"
                className="fs-3 text-light text-decoration-none"
            >
                Projects
            </Link>

            <Notifications />
        </header>
    );
}
