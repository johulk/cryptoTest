import Aside from "../components/Aside.jsx";
import Header from '../components/Header.jsx';
import Home from '../components/Home.jsx';

export default function HomePage() {
    return (
        <section>
            <Header/>
            <div className="bellow-header">
                <Aside/>
                <Home/>
            </div>
        </section>
    );
}