import { Link } from "react-router-dom";

export default function Header() {
    return (
        <header className="p-3 bg-dark">
            <Link to="/" className="fs-3 text-light text-decoration-none">
                Projects
            </Link>
        </header>
    );
}
