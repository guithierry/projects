import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";

import { BrowserRouter, Routes, Route } from "react-router-dom";

import Projects from "./components/Projects";
import Todos from "./components/Todos";

export default function App() {
    return (
        <DndProvider backend={HTML5Backend}>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Projects />} />
                    <Route path="/:id" element={<Todos />} />
                </Routes>
            </BrowserRouter>
        </DndProvider>
    );
}
