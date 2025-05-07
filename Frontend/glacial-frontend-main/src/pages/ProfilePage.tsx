import Header from '../components/Header.jsx';
import Aside from '../components/Aside.jsx';
import Profile from "../components/Profile.jsx";

export default function ProfilePage() {
    return (
        <section>
            {/*
            <Header/>
            <Aside/>
            <Profile/>
            */}
            <Header/>
            <div className="bellow-header">
                <Aside/>
                <Profile/>
            </div>
        </section>
    );
}