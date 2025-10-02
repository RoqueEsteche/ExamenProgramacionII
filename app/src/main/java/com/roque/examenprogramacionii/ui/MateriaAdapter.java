package com.roque.examenprogramacionii.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.roque.examenprogramacionii.data.db.Materia;
import com.roque.examenprogramacionii.databinding.ItemMateriaBinding;

public class MateriaAdapter extends ListAdapter<Materia, MateriaAdapter.MateriaViewHolder> {

    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;

    public MateriaAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Materia> DIFF_CALLBACK = new DiffUtil.ItemCallback<Materia>() {
        @Override
        public boolean areItemsTheSame(@NonNull Materia oldItem, @NonNull Materia newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Materia oldItem, @NonNull Materia newItem) {
            return oldItem.nombre.equals(newItem.nombre) && oldItem.curso.equals(newItem.curso);
        }
    };

    @NonNull
    @Override
    public MateriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMateriaBinding binding = ItemMateriaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MateriaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaViewHolder holder, int position) {
        Materia currentMateria = getItem(position);
        holder.bind(currentMateria);
    }

    public Materia getMateriaAt(int position) {
        return getItem(position);
    }

    class MateriaViewHolder extends RecyclerView.ViewHolder {
        private final ItemMateriaBinding binding;

        public MateriaViewHolder(ItemMateriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.infoContainer.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });

            binding.buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (deleteListener != null && position != RecyclerView.NO_POSITION) {
                    deleteListener.onDeleteClick(getItem(position));
                }
            });
        }

        public void bind(Materia materia) {
            binding.textViewNombreMateria.setText(materia.nombre);
            binding.textViewCurso.setText("Curso: " + materia.curso);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Materia materia);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Materia materia);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }
}
