//
// Created by allo cai on 2022/1/2.
//

#include "GLNativeRenderProc.h"

GLint program;
float vertexBuffer[9] = {0.0f, 0.5f, 0.0f, // top
                         -0.5f, -0.5f, 0.0f, // bottom left
                         0.5f, -0.5f, 0.0f};


void loadGLProgram(const char * vertexCode,const char * fragmentCode) {
   program = createProgram(vertexCode,fragmentCode);

}

void SetViewPortSize(float width,float height) {
    glViewport(0,0,width,height);
}

void renderImpl() {
    glClear(GL_COLOR_BUFFER_BIT);
    glUseProgram(program);
    glEnableVertexAttribArray(0);
    glVertexAttribPointer(0,3,GL_FLOAT,GL_FALSE,0,vertexBuffer);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 3);
    glDisableVertexAttribArray(0);
}
