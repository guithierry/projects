import { useLocation, Navigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";

export default function RequiresAuth({ children }: { children: any }) {
    const { user } = useAuth();
    const location = useLocation();

    if (!user.id && !user.token) {
        return (
            <Navigate
                to="/"
                state={{
                    from: location.pathname,
                }}
                replace
            />
        );
    }

    return children;
}
