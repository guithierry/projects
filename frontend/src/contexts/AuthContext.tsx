import { createContext, useState } from "react";
import { User } from "../types";

type Payload = User & { token: string };

interface AuthProviders {
    user: Payload;
    handleAuth(props: Payload): void;
}

export const AuthContext = createContext<AuthProviders | null>(null);

export default function AuthProvider({ children }: { children: any }) {
    const [user, setUser] = useState<User>({} as User);

    function handleAuth(props: User) {
        setUser(props);
    }

    return (
        <AuthContext.Provider value={{ user, handleAuth }}>
            {children}
        </AuthContext.Provider>
    );
}
