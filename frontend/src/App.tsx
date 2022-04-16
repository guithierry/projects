import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";

import { BrowserRouter, Routes, Route } from "react-router-dom";

import AuthProvider from "./contexts/AuthContext";

import Authentication from "./components/Authentication";
import RequiresAuth from "./components/RequiresAuth";
import Projects from "./components/Projects";
import Todos from "./components/Todos";

export default function App() {
    return (
        <DndProvider backend={HTML5Backend}>
            <AuthProvider>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<Authentication />} />
                        <Route
                            path="/projects"
                            element={
                                <RequiresAuth>
                                    <Projects />
                                </RequiresAuth>
                            }
                        />
                        <Route
                            path="/projects/:id"
                            element={
                                <RequiresAuth>
                                    <Todos />
                                </RequiresAuth>
                            }
                        />
                    </Routes>
                </BrowserRouter>
            </AuthProvider>
        </DndProvider>
    );
}
