package com.android.store.mercapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.store.mercapp.Entidades.Productos;
import com.android.store.mercapp.Entidades.Storage;
import com.android.store.mercapp.Fragments.FragmentProductDetail;
import com.android.store.mercapp.Fragments.FragmentProducto;
import com.android.store.mercapp.Fragments.FragmentStorage;
import com.android.store.mercapp.Interfaces.CommunicationInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentStorage.OnFragmentInteractionListener,
        FragmentProducto.OnFragmentInteractionListener,
        FragmentProductDetail.OnFragmentInteractionListener,
        ExampleDialog.DialogListener,ProductDialog.DialogListenerP, CommunicationInterface {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private  static final String TAG="Log";
    public int optionSelected=1;
    Fragment listastore = new FragmentStorage();
    FragmentProducto productoF = new FragmentProducto();
    public static String idtienda;
    private  FloatingActionButton fab;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,listastore, "MAIN_FRAGMENT").commit();

        fab = findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (optionSelected){
                    case 1:
                        Toast.makeText(getApplicationContext(), "TIENDAS", Toast.LENGTH_SHORT).show();
                        openDialog();
                        break;
                    case 2:

                        break;
                    case 3:
                        /*Toast.makeText(getApplicationContext(), "CLIENTES", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),RegitroCientes.class);
                        startActivity(intent);*/
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Grafica", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "Materias", Toast.LENGTH_SHORT).show();
                        break;
                }
            }


        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        UpdateNavHeader();


    }



    private void openDialogProductos() {
        ProductDialog productDialog = new ProductDialog();
        productDialog.show(getSupportFragmentManager(), "dialog");
    }

    private void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "dialog");
    }

    private void goLoginScreen() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment myFragment = getSupportFragmentManager().findFragmentByTag("MAIN_FRAGMENT");

            if (myFragment != null && myFragment.isVisible()) {
                super.onBackPressed();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,listastore, "MAIN_FRAGMENT").commit();
                optionSelected=1;

            }
            //MAIN_FRAGMENT
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem searchitem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add_products ){
            Toast.makeText(getApplicationContext(), "PRODUCTOS", Toast.LENGTH_SHORT).show();
            openDialogProductos();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        boolean FragmentSelected=false;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_store) {
            fragment =new FragmentStorage();
            FragmentSelected=true;
            optionSelected=1;
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_product) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            //LoginManager.getInstance().logOut();
             FirebaseAuth.getInstance().signOut();
             goLoginScreen();
        }
        if (FragmentSelected){
            fragmentManager.beginTransaction().replace(R.id.Contenedor, fragment).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void UpdateNavHeader(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.txtNameProfile);
        TextView navUseremail = headerView.findViewById(R.id.txtEmailProfile);
        ImageView navUserPhoto = headerView.findViewById(R.id.profile_image);

        navUsername.setText(currentUser.getDisplayName());
        navUseremail.setText(currentUser.getEmail());
        // usamos glide para la foto

        Glide.with(this).load(currentUser.getPhotoUrl()).apply(new RequestOptions().placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)).centerCrop().into(navUserPhoto);

        Log.d(TAG, "Nombre de usuario : " + navUsername);
    }

    @Override
    public void RegisterStore(String Nombre, String Direccion, String Estado, String id, String link) {
                Toast.makeText(getApplicationContext(),"DATOS TIENDA : " + "NOMBRE :" +Nombre+ "DIRECCION" +Direccion + "ESTADO" + Estado,Toast.LENGTH_SHORT).show();
        final Storage storage = new Storage(Nombre,Direccion,Estado,id, link);
        FirebaseFirestore.getInstance().collection("Tiendas")
                .document(id).set(storage).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Tienda registrada existosamente", Toast.LENGTH_SHORT).show();
            }
        });

    }
    // funcion para setear el valor de idtiendaP en la variable global idtienda ; Esta función es llamada en el FragmentProducto
    public void setIdtiendaAproducto(String idtiendaP){
        idtienda = idtiendaP;
    }




    @Override
    public void RegisterProducts(String nombreProducto, int PrecioProducto, String idproducto, String idImage) {

        final Productos productos = new Productos(PrecioProducto,nombreProducto,idproducto,idImage);
        if (idtienda!=null) {
            FirebaseFirestore.getInstance()
                    .collection("Tiendas")
                    .document(idtienda)
                    .collection("Productos")
                    .document()
                    .set(productos).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Producto registrado existosamente", Toast.LENGTH_SHORT).show();
                }
            });
        }

        System.out.println("EL ID DE LA TIENDA ES "+idtienda);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }




    @Override
    public void sendData(Productos productos) {
        fab.setVisibility(View.INVISIBLE);
        FragmentProductDetail productDetail = new FragmentProductDetail();
        Bundle bundleP = new Bundle();
        bundleP.putSerializable("objeto",productos);
        productDetail.setArguments(bundleP);

        getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,productDetail).addToBackStack(null).commit();
    }

}
