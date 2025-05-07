import Header from "../components/Header.jsx";
import Aside from "../components/Aside.jsx";

function Profile() {
    return (
        <section>
            <Header/>
            <div className="bellow-header">
                <Aside/>
            </div>
        </section>
    );
}

export default Profile;